package io.maxilog.service.mapper;


import io.maxilog.domain.Address;
import io.maxilog.service.dto.AddressDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Address and its DTO AddressDTO.
 */
@Mapper(componentModel = "cdi", uses = {})
public interface AddressMapper extends EntityMapper <AddressDTO, Address> {
}
