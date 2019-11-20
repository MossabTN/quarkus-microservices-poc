package io.maxilog.service;

import io.maxilog.service.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing categoryDTO.
 */
public interface CategoryService {

    /**
     * Save a category.
     *
     * @param categoryDTO the entity to save
     * @return the persisted entity
     */
    CategoryDTO save(CategoryDTO categoryDTO);

    /**
     * Get all the categoryDTO.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CategoryDTO> findAll(Pageable pageable);


    /**
     * Get all the categoryDTO by category name.
     *
     * @param name the filter information
     * @return the list of entities
     */
    List<CategoryDTO> findAllByName(String name);

    /**
     * Get the "id" category.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CategoryDTO findOne(long id);


    /**
     *  Delete the "id" category.
     *
     *  @param id the id of the entity
     */
    void delete(long id);

}
