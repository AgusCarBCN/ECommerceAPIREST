package com.carnerero.agustin.ecommerceapplication.dtos.responses;


import com.carnerero.agustin.ecommerceapplication.model.enums.OrderStatus;
import com.carnerero.agustin.ecommerceapplication.model.enums.ShippingMethod;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class OrderResponseDTO {
        private Long id;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private OrderStatus status;
        private ShippingMethod shippingMethod;
        private BigDecimal totalAmount;// ✅ Aquí va el total
        private List<ProductResponseDTO> products;


    }



