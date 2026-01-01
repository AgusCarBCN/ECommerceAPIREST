package com.carnerero.agustin.ecommerceapplication.dtos.responses;


import com.carnerero.agustin.ecommerceapplication.model.enums.OrderStatus;
import com.carnerero.agustin.ecommerceapplication.model.enums.ShippingMethod;
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
