package com.carnerero.agustin.tech_e_commerce.dtos.responses;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderProductResponseDTO {

    private Integer quantity;
    //Product detail
    private ProductCatalogResponseDTO productDetail;
}
