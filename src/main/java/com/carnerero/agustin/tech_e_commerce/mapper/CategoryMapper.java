package com.carnerero.agustin.tech_e_commerce.mapper;

import com.carnerero.agustin.tech_e_commerce.dtos.requests.CategoryDTO;
import com.carnerero.agustin.tech_e_commerce.dtos.requests.CategoryRequestDTO;
import com.carnerero.agustin.tech_e_commerce.entities.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryEntity toEntity(CategoryRequestDTO categoryRequestDTO);
    CategoryDTO toCategoryDTO(CategoryEntity categoryEntity);
}
