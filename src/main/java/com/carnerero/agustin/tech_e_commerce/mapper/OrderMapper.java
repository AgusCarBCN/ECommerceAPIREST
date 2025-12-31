package com.carnerero.agustin.tech_e_commerce.mapper;

import com.carnerero.agustin.tech_e_commerce.dtos.requests.OrderRequestDTO;
import com.carnerero.agustin.tech_e_commerce.dtos.responses.OrderResponseDTO;
import com.carnerero.agustin.tech_e_commerce.entities.BillEntity;
import com.carnerero.agustin.tech_e_commerce.entities.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses={UserMapper.class,BillMapper.class})
public interface OrderMapper {

    OrderResponseDTO toOrderResponseDTO(OrderEntity orderEntity);
    OrderEntity toOrderEntity(OrderRequestDTO orderRequestDTO);
}
