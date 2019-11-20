package io.maxilog.service;

import io.maxilog.chat.NotificationKafka;

/**
 * Service Interface for managing notificationDTO.
 */
public interface NotificationService {

    /**
     * Notify.
     *
     * @param notification the payload to notify
     */
    void publishKafka(NotificationKafka notification);

}
