package io.maxilog.service;

import io.maxilog.service.dto.Page;
import io.maxilog.service.dto.PageableImpl;
import io.maxilog.service.dto.UserDTO;

import java.util.List;

/**
 * Service Interface for managing userDTO.
 */
public interface UserService {

    /**
     * Save a user.
     *
     * @param userDTO the entity to save
     * @return the persisted entity
     */
    UserDTO save(UserDTO userDTO);
    /**
     * Save a user.
     *
     * @param userDTO the entity to save
     * @return the persisted entity
     */
    UserDTO update(UserDTO userDTO);

    /**
     * Get all the userDTO.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    List<UserDTO> findAll(PageableImpl pageable);
    /**
     * Get all the userDTO.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserDTO> findPage(PageableImpl pageable);

    /**
     * Get the "id" user.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UserDTO findOne(String id);

    /**
     * Get the "username" user.
     *
     * @param username the username of the entity
     * @return the entity
     */
    UserDTO findUsername(String username);

    /**
     * Get my user.
     *
     * @return the entity
     */
    UserDTO findMyData();


    /**
     *  Delete the "id" user.
     *
     *  @param id the id of the entity
     */
    void delete(String id);

}
