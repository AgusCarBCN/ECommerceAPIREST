package com.carnerero.agustin.tech_e_commerce.mapper;

import com.carnerero.agustin.tech_e_commerce.dtos.requests.OrderProductRequestDTO;
import com.carnerero.agustin.tech_e_commerce.dtos.responses.OrderProductResponseDTO;
import com.carnerero.agustin.tech_e_commerce.entities.OrderProductsEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses={
        ProductCatalogMapper.class
})
public interface OrderProductMapper {
    OrderProductResponseDTO toOrderProductResponse(OrderProductsEntity orderProductsEntity);
    OrderProductsEntity toOrderProductsEntity(OrderProductRequestDTO orderProductRequestDTO);
}