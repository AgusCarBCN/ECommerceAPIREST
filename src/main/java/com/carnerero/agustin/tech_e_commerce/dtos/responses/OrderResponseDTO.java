package com.carnerero.agustin.tech_e_commerce.dtos.responses;


import com.carnerero.agustin.tech_e_commerce.entities.OrderStatus;
import com.carnerero.agustin.tech_e_commerce.entities.ShippingMethod;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponseDTO {
    private UserResponseDTO user;
    private BillResponseDTO bill;
    private OrderStatus orderStatus;
    private ShippingMethod shippingMethod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
