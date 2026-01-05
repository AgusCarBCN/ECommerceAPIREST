package com.carnerero.agustin.ecommerceapplication.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDTO {

    private Integer quantity;
    private BigDecimal price;
    //Product detail
    private ProductCatalogResponseDTO productCatalog;
}
