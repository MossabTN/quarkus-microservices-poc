package io.maxilog.service;

import io.maxilog.chat.NotificationKafka;
import io.maxilog.domain.Notification;
import io.maxilog.service.dto.NotificationDTO;
import io.quarkus.panache.common.Page;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;

import java.util.List;

/**
 * Service Interface for managing notificationDTO.
 */
public interface NotificationService {

    /**
     * Save a notification.
     *
     * @param notificationDTO the entity to save
     * @return the persisted entity
     */
    NotificationDTO save(NotificationDTO notificationDTO);

    /**
     * Notify.
     *
     * @param notification the payload to notify
     */
    void notify(NotificationDTO notification);

    /**
     * publish.
     *
     * @param notificationKafka
     */
    void publish(NotificationKafka notificationKafka);

    /**
     * Get all the notificationDTO.
     *
     * @param page
     *
     * @return the list of entities
     */
    List<NotificationDTO> findAll(Page page);


    /**
     * Get the "id" notification.
     *
     * @param id the id of the entity
     * @return the entity
     */
    NotificationDTO findOne(String id);

    /**
     * Get my notification.
     *
     * @param page
     *
     * @return the entity
     */
    List<NotificationDTO> findMyNotifications(Page page);


    /**
     *  Delete the "id" notification.
     *
     *  @param id the id of the entity
     */
    void delete(String id);

    void onOpen(BridgeEvent event, EventBus eventBus);

    void onMessage(BridgeEvent event, EventBus eventBus);

    void onClose(BridgeEvent event, EventBus eventBus);

}
