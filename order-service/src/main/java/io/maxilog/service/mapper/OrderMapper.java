package io.maxilog.service.mapper;


import io.maxilog.domain.Order;
import io.maxilog.service.dto.OrderDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Order and its DTO OrderDTO.
 */
//@Mapper(componentModel = "cdi", uses = {PaymentMapper.class, AddressMapper.class, OrderItemMapper.class})
public interface OrderMapper extends EntityMapper <OrderDTO, Order> {
}
