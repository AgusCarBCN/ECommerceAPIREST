package com.carnerero.agustin.ecommerceapplication.util.mapper;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.ProductRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.ProductResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses={
        ProductCatalogMapper.class
})
public interface ProductMapper {
    ProductResponseDTO toOrderProductResponse(ProductEntity orderProductsEntity);
    ProductEntity toOrderProductsEntity(ProductRequestDTO orderProductRequestDTO);
}