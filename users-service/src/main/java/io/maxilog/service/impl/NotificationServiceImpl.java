package io.maxilog.service.impl;

import io.maxilog.chat.NotificationKafka;
import io.maxilog.service.NotificationService;
import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NotificationServiceImpl implements NotificationService {

    @Inject
    @Channel("notification")
    private Emitter<NotificationKafka> kafkaEmitter;

    @Override
    public void publishKafka(NotificationKafka notification) {
        kafkaEmitter.send(notification);
    }
}
