package com.carnerero.agustin.ecommerceapplication.util.mapper;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.CategoryDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.CategoryRequestDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryEntity toEntity(CategoryRequestDTO categoryRequestDTO);
    CategoryDTO toCategoryDTO(CategoryEntity categoryEntity);
}
