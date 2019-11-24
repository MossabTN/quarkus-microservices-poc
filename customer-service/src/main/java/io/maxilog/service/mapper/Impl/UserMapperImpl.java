package io.maxilog.service.mapper.Impl;

import io.maxilog.domain.UserRepresentation;
import io.maxilog.service.dto.UserDTO;
import io.maxilog.service.mapper.EntityMapper;

import javax.inject.Singleton;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Mapper for the entity UserRepresentation and its DTO UserDTO.
 */

@Singleton
public class UserMapperImpl implements EntityMapper <UserDTO, UserRepresentation> {
    @Override
    public UserRepresentation toEntity(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId(dto.getId());
        userRepresentation.setUsername(dto.getUsername());
        userRepresentation.setEmail(dto.getEmail());
        userRepresentation.setFirstName(dto.getFirstName());
        userRepresentation.setLastName(dto.getLastName());
        Map<String, List<String>> map = new HashMap<>();
        map.put("phoneNumber", Collections.singletonList(dto.getPhoneNumber()));
        userRepresentation.setAttributes(map);
        userRepresentation.setEnabled(dto.isEnabled());
        return userRepresentation;
    }

    @Override
    public UserDTO toDto(UserRepresentation entity) {
        if ( entity == null ) {
            return null;
        }
        UserDTO user = new UserDTO();
        user.setId(entity.getId());
        user.setUsername(entity.getUsername());
        user.setEmail(entity.getEmail());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setPhoneNumber(Optional.ofNullable(entity.getAttributes()).map(map -> map.get("phoneNumber")).flatMap(strings -> strings.stream().findFirst()).orElse(null));
        user.setEnabled(entity.getEnabled());
        return user;
    }

    @Override
    public List<UserRepresentation> toEntity(List<UserDTO> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> toDto(List<UserRepresentation> entityList) {
        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}