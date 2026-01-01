package com.carnerero.agustin.ecommerceapplication.dtos.responses;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductCatalogResponseDTO {

    private String productName;
    private String description;
    private BigDecimal price;
    private Long stockQuantity;
    private LocalDateTime createdAt;
}
