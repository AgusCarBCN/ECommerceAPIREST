package com.carnerero.agustin.tech_e_commerce.mapper;

import com.carnerero.agustin.tech_e_commerce.dtos.requests.ProductCatalogRequestDTO;
import com.carnerero.agustin.tech_e_commerce.dtos.responses.ProductCatalogResponseDTO;
import com.carnerero.agustin.tech_e_commerce.entities.ProductCatalogEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = CategoryMapper.class)
public interface ProductCatalogMapper {

    ProductCatalogResponseDTO toProductCatalogResponseDTO(ProductCatalogEntity productCatalogEntity);
    ProductCatalogEntity toProductCatalogEntity(ProductCatalogRequestDTO productCatalogRequestDTO);
}
