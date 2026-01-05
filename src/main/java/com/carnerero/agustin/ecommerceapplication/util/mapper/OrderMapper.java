package com.carnerero.agustin.ecommerceapplication.util.mapper;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.OrderRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.OrderResponseDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.ProductCatalogResponseDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.ProductResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.OrderEntity;
import com.carnerero.agustin.ecommerceapplication.model.entities.ProductCatalogEntity;
import com.carnerero.agustin.ecommerceapplication.model.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring",uses={
        UserMapper.class,
        BillMapper.class,
        ProductMapper.class,
        ProductCatalogMapper.class})
public interface OrderMapper {

    @Mapping(source = "products", target = "products")
    OrderResponseDTO toOrderResponseDTO(OrderEntity orderEntity);
    // 👇 este método le enseña a MapStruct cómo mapear la lista

    default List<ProductResponseDTO> mapProducts(List<ProductEntity> products) {
        if (products == null) return List.of();

        return products.stream()
                .map(p -> ProductResponseDTO.builder()
                        .quantity(p.getQuantity())
                        .price(p.getProductCatalog().getPrice())
                        .productCatalog(mapCatalog(p.getProductCatalog()))
                        .build())
                .toList();
    }


    ProductCatalogResponseDTO mapCatalog(ProductCatalogEntity entity);


    OrderEntity toOrderEntity(OrderRequestDTO orderRequestDTO);

}
