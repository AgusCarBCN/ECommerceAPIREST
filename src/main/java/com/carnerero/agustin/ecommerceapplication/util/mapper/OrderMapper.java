package com.carnerero.agustin.ecommerceapplication.util.mapper;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.OrderRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.OrderResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses={UserMapper.class,BillMapper.class})
public interface OrderMapper {

    OrderResponseDTO toOrderResponseDTO(OrderEntity orderEntity);
    OrderEntity toOrderEntity(OrderRequestDTO orderRequestDTO);
}
