package io.maxilog.service.impl;

import io.maxilog.client.keycloakClient;
import io.maxilog.domain.UserHolder;
import io.maxilog.service.UserService;
import io.maxilog.service.dto.PageableImpl;
import io.maxilog.service.dto.UserDTO;
import io.maxilog.service.mapper.Impl.UserMapperImpl;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserHolder userHolder;
    private final keycloakClient keycloakClient;
    private final UserMapperImpl userMapper;

    @Inject
    public UserServiceImpl(@RestClient keycloakClient keycloakClient, UserMapperImpl userMapper, UserHolder userHolder) {
        this.userHolder = userHolder;
        this.keycloakClient = keycloakClient;
        this.userMapper = userMapper;
    }


    @Override
    public UserDTO save(UserDTO userDTO) {
        LOG.debug("Request to save Users : {}", userDTO);
        keycloakClient.createUser(userMapper.toEntity(userDTO));
        //return userMapper.toDto(user);
        return userDTO;
    }

    @Override
    public List<UserDTO> findAll(PageableImpl pageable){
        LOG.debug("Request to get all Users");
        return keycloakClient.getUsersPageable(pageable.getPage(), pageable.getSize())
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findOne(String id) {
        LOG.debug("Request to get User : {}", id);
        return userMapper.toDto(keycloakClient.getUserById(id));
    }

    @Override
    public UserDTO findUsername(String username) {
        LOG.debug("Request to get User by username: {}", username);
        return keycloakClient.getUsersByUsername(username)
                .stream()
                .findFirst()
                .map(userMapper::toDto)
                .orElse(null);
    }

    @Override
    public UserDTO findMyData() {
        return keycloakClient.getUsersByUsername(userHolder.getUserName())
                .stream()
                .map(userMapper::toDto)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete User : {}", id);
        keycloakClient.deleteUser(id);
    }
}
