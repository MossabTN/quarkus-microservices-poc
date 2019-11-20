package io.maxilog.service;

import io.maxilog.service.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing orderDTO.
 */
public interface OrderService {

    /**
     * Save a order.
     *
     * @param orderDTO the entity to save
     * @return the persisted entity
     */
    OrderDTO save(OrderDTO orderDTO);

    /**
     * Get all the orderDTO.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OrderDTO> findAll(Pageable pageable);


    /**
     * Get the "id" order.
     *
     * @param id the id of the entity
     * @return the entity
     */
    OrderDTO findOne(long id);

    /**
     * Get my order.
     *
     * @return the entity
     */
    List<OrderDTO> findMyOrders();


    /**
     *  Delete the "id" order.
     *
     *  @param id the id of the entity
     */
    void delete(long id);

}
