package com.carnerero.agustin.ecommerceapplication.util.mapper;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.OrderProductRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.OrderProductResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.OrderProductsEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses={
        ProductCatalogMapper.class
})
public interface OrderProductMapper {
    OrderProductResponseDTO toOrderProductResponse(OrderProductsEntity orderProductsEntity);
    OrderProductsEntity toOrderProductsEntity(OrderProductRequestDTO orderProductRequestDTO);
}