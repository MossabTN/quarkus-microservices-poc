package io.maxilog.service.mapper;


import io.maxilog.chat.NotificationKafka;
import io.maxilog.domain.Notification;
import io.maxilog.service.dto.NotificationDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Notification and its DTO NotificationKafka.
 */
@Mapper(componentModel = "cdi", uses = {})
public interface NotificationKafkaMapper extends EntityMapper <NotificationKafka, Notification> {
}
