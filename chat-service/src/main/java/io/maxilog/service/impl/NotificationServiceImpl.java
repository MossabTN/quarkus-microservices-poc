package io.maxilog.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.maxilog.chat.NotificationKafka;
import io.maxilog.domain.Notification;
import io.maxilog.domain.UserHolder;
import io.maxilog.repository.impl.NotificationRepository;
import io.maxilog.service.NotificationService;
import io.maxilog.service.dto.NotificationDTO;
import io.maxilog.service.mapper.NotificationKafkaMapper;
import io.maxilog.service.mapper.NotificationMapper;
import io.quarkus.panache.common.Page;
import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;
import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Singleton
public class NotificationServiceImpl implements NotificationService {

    private static final Logger LOG = Logger.getLogger(NotificationService.class);
    private static final String PREFERRED_USERNAME = "username";
    private Map<String, BridgeEvent> bridgeEvents = new ConcurrentHashMap<>();
    private ObjectMapper objectMapper;

    private final EventBus eventBus;
    private final NotificationRepository notificationRepository;
    private final Emitter<NotificationKafka> kafkaEmitter;
    private final NotificationMapper notificationMapper;
    private final NotificationKafkaMapper notificationKafkaMapper;
    private final UserHolder userHolder;

    @Inject
    public NotificationServiceImpl(EventBus eventBus,
                                   NotificationRepository notificationRepository,
                                   @Channel("notification") Emitter<NotificationKafka> kafkaEmitter,
                                   NotificationMapper notificationMapper,
                                   NotificationKafkaMapper notificationKafkaMapper,
                                   UserHolder userHolder) {
        this.eventBus = eventBus;
        this.notificationRepository = notificationRepository;
        this.kafkaEmitter = kafkaEmitter;
        this.notificationMapper = notificationMapper;
        this.notificationKafkaMapper = notificationKafkaMapper;
        this.userHolder = userHolder;
        objectMapper = new ObjectMapper();
    }


    @Override
    public NotificationDTO save(NotificationDTO notificationDTO) {
        Notification notification = notificationMapper.toEntity(notificationDTO);
        notificationRepository.persist(notification);
        return notificationMapper.toDto(notification);
    }

    @Override
    public void notify(NotificationDTO notification) {
        save(notification);
        if(notification.getTo().equals("ALL")){
            LOG.info("Send notification to all users");
            eventBus.publish("out", parseToString(notification));
        }else{
            LOG.info("Send notification to username");
            Optional.ofNullable(this.bridgeEvents.get("username"))
                .ifPresent(bridgeEvent -> {
                    bridgeEvent.socket().write(parseToString(notification));
                });
        }
    }

    @Override
    public void publish(NotificationKafka notification) {
        this.kafkaEmitter.send(notification);
    }

    @Incoming("input")
    public CompletionStage consumeKafkaNotification(KafkaMessage<String, NotificationKafka> message) {
        LOG.info("receiving notification from kafka");
        //notify(notificationMapper.toDto(notificationKafkaMapper.toEntity(message.getPayload())));
        System.out.println(notificationMapper.toDto(notificationKafkaMapper.toEntity(message.getPayload())));
        return message.ack();
    }

    @Override
    public List<NotificationDTO> findAll(Page page) {
        return notificationRepository.findAll(page)
                .stream()
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDTO findOne(String id) {
        return notificationMapper.toDto(notificationRepository.findById(new ObjectId(id)));
    }

    @Override
    public List<NotificationDTO> findMyNotifications(Page page) {
        return notificationRepository.findAllByTo(userHolder.getUserName(), page)
                .stream()
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        Notification.findById(new ObjectId(id)).delete();
    }

    @Override
    public void onOpen(BridgeEvent event, EventBus eventBus) {
        LOG.info("A socket was created for "+ event.socket().webUser().principal().getString(PREFERRED_USERNAME));
        bridgeEvents.put(event.socket().webUser().principal().getString(PREFERRED_USERNAME), event);
        event.socket().write(newMessage("Welcome "+event.socket().webUser().principal().getString(PREFERRED_USERNAME)));
        eventBus.publish("out", new JsonObject().put("body", "Notification " + event.socket().webUser().principal().getString(PREFERRED_USERNAME) + " joined").toString());
    }

    @Override
    public void onMessage(BridgeEvent event, EventBus eventBus) {
        LOG.info("Message From "+ event.socket().webUser().principal().getString(PREFERRED_USERNAME));
        JsonObject jsonObject = event.getRawMessage();
        jsonObject.put("body", event.socket().webUser().principal().getString(PREFERRED_USERNAME)+": "+jsonObject.getString("body"));
        eventBus.publish("out", jsonObject.toString());
    }

    @Override
    public void onClose(BridgeEvent event, EventBus eventBus) {
        LOG.info("A socket of "+ event.socket().webUser().principal().getString(PREFERRED_USERNAME)+" has been closed");
        bridgeEvents.remove(event.socket().webUser().principal().getString(PREFERRED_USERNAME));
    }

    private String parseToString(NotificationKafka notification) {
        try {
            return objectMapper.writeValueAsString(notification);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
    private String parseToString(NotificationDTO notification) {
        try {
            return objectMapper.writeValueAsString(notification);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    private String newMessage(String message) {
        return new JsonObject()
                .put("body", new JsonObject()
                        .put("body", message)
                        .toString())
                .put("address", "out")
                .toString();
    }
}
