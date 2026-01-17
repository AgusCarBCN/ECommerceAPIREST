package com.carnerero.agustin.ecommerceapplication.util.mapper;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.CategoryRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.CreateCategoryRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.CategoryResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryEntity toEntity(CategoryRequestDTO categoryRequestDTO);
    CategoryEntity toCreateCategoryEntity(CreateCategoryRequestDTO createCategoryRequestDTO);
    CategoryResponseDTO toCategoryDTO(CategoryEntity categoryEntity);

}
