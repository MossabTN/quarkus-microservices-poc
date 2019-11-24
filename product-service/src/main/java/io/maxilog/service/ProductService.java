package io.maxilog.service;

import io.maxilog.service.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing ProductDTO.
 */
public interface ProductService {

    /**
     * Save a user.
     *
     * @param productDTO the entity to save
     * @return the persisted entity
     */
    ProductDTO save(ProductDTO productDTO);

    /**
     * Get all the ProductDTO.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProductDTO> findAll(Pageable pageable);


    /**
     * Get all the ProductDTO by username.
     *
     * @param name the filter information
     * @return the list of entities
     */
    Page<ProductDTO> findAllByName(String name, Pageable pageable);

    /**
     * Get the "id" user.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProductDTO findOne(long id);

    /**
     *  Delete the "id" user.
     *
     *  @param id the id of the entity
     */
    void delete(long id);

}
