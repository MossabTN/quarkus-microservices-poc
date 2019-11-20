package io.maxilog.service.mapper;


import io.maxilog.domain.Payment;
import io.maxilog.service.dto.PaymentDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Payment and its DTO PaymentDTO.
 */
@Mapper(componentModel = "cdi", uses = {OrderMapper.class})
public interface PaymentMapper extends EntityMapper <PaymentDTO, Payment> {
}
