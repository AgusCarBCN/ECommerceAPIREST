package com.carnerero.agustin.ecommerceapplication.util.mapper;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.ProductCatalogRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.ProductCatalogResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.ProductCatalogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = CategoryMapper.class)
public interface ProductCatalogMapper {

    ProductCatalogResponseDTO toProductCatalogResponseDTO(ProductCatalogEntity productCatalogEntity);
    ProductCatalogEntity toProductCatalogEntity(ProductCatalogRequestDTO productCatalogRequestDTO);


}
