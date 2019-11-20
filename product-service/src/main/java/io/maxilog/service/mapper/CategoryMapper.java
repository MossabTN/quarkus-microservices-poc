package io.maxilog.service.mapper;


import io.maxilog.domain.Category;
import io.maxilog.service.dto.CategoryDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Category and its DTO CategoryDTO.
 */
@Mapper(componentModel = "cdi", uses = {})
public interface CategoryMapper extends EntityMapper <CategoryDTO, Category> {
}
