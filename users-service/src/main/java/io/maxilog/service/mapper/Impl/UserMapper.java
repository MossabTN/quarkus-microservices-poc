package io.maxilog.service.mapper.Impl;

import io.maxilog.domain.UserRepresentation;
import io.maxilog.service.dto.UserDTO;
import io.maxilog.service.mapper.EntityMapper;

import javax.inject.Singleton;
import java.util.List;

/**
 * Mapper for the entity UserRepresentation and its DTO UserDTO.
 */

@Singleton
public class UserMapper implements EntityMapper <UserDTO, UserRepresentation> {
    @Override
    public UserRepresentation toEntity(UserDTO dto) {
        return null;
    }

    @Override
    public UserDTO toDto(UserRepresentation entity) {
        return null;
    }

    @Override
    public List<UserRepresentation> toEntity(List<UserDTO> dtoList) {
        return null;
    }

    @Override
    public List<UserDTO> toDto(List<UserRepresentation> entityList) {
        return null;
    }
}