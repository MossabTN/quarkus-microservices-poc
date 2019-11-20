package io.maxilog.service.mapper;


import io.maxilog.domain.Product;
import io.maxilog.service.dto.ProductDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "cdi", uses = {CategoryMapper.class})
public interface ProductMapper extends EntityMapper <ProductDTO, Product> {
}
