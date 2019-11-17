package io.maxilog.service.mapper;


import io.maxilog.domain.Notification;
import io.maxilog.service.dto.NotificationDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Notification and its DTO NotificationDTO.
 */
@Mapper(componentModel = "cdi", uses = {})
public interface NotificationMapper extends EntityMapper <NotificationDTO, Notification> {
}
