package io.maxilog.service.impl;

import io.maxilog.chat.NotificationKafka;
import io.maxilog.client.keycloakClient;
import io.maxilog.domain.UserHolder;
import io.maxilog.service.UserService;
import io.maxilog.service.dto.Page;
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
    private final NotificationServiceImpl notificationService;
    private final keycloakClient keycloakClient;
    private final UserMapperImpl userMapper;

    @Inject
    public UserServiceImpl(@RestClient keycloakClient keycloakClient, UserMapperImpl userMapper, UserHolder userHolder, NotificationServiceImpl notificationService) {
        this.userHolder = userHolder;
        this.keycloakClient = keycloakClient;
        this.userMapper = userMapper;
        this.notificationService = notificationService;
    }


    @Override
    public UserDTO save(UserDTO userDTO) {
        LOG.info("Request to save Users : {}", userDTO);
        keycloakClient.createUser(userMapper.toEntity(userDTO));
        //return userMapper.toDto(user);
        //notificationService.publishKafka(new NotificationKafka("SYSTEM", "SYSTEM","NEW_CUSTOMER", "NOTIFICATION", false));
        return userDTO;
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        keycloakClient.updateUser(userDTO.getId(), userMapper.toEntity(userDTO));
        return userDTO;
    }

    @Override
    public List<UserDTO> findAll(PageableImpl pageable){
        LOG.info("Request to get all Users");
        return keycloakClient.getUsersPageable(pageable.getPage(), pageable.getSize())
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserDTO> findPage(PageableImpl pageable) {
        return new Page<>(findAll(pageable), keycloakClient.countUsers());
    }

    @Override
    public UserDTO findOne(String id) {
        LOG.info("Request to get User : {}", id);
        return userMapper.toDto(keycloakClient.getUserById(id));
    }

    @Override
    public UserDTO findUsername(String username) {
        LOG.info("Request to get User by username: {}", username);
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
        LOG.info("Request to delete User : {}", id);
        keycloakClient.deleteUser(id);
    }
}
