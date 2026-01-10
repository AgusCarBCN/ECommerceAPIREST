package com.carnerero.agustin.ecommerceapplication.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductCatalogResponseDTO {

    private String productName;
    private String description;
    private BigDecimal price;
    private BigDecimal discountPrice;
}
