package com.carnerero.agustin.ecommerceapplication.dtos.responses;


import com.carnerero.agustin.ecommerceapplication.model.enums.OrderStatus;
import com.carnerero.agustin.ecommerceapplication.model.enums.ShippingMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponseDTO {
    private UserResponseDTO user;
    private BillResponseDTO bill;
    private OrderStatus status;
    private ShippingMethod shippingMethod;
    private List<ProductCatalogResponseDTO> products;
    //private LocalDateTime createdAt;
    //private LocalDateTime updatedAt;
}
