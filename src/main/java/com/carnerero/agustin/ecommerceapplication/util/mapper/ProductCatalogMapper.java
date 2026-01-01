package com.carnerero.agustin.ecommerceapplication.util.mapper;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.ProductCatalogRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.ProductCatalogResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.ProductCatalogEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = CategoryMapper.class)
public interface ProductCatalogMapper {

    ProductCatalogResponseDTO toProductCatalogResponseDTO(ProductCatalogEntity productCatalogEntity);
    ProductCatalogEntity toProductCatalogEntity(ProductCatalogRequestDTO productCatalogRequestDTO);
}
