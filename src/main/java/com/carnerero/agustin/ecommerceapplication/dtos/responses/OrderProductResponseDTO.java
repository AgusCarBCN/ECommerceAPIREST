package com.carnerero.agustin.ecommerceapplication.dtos.responses;

import lombok.Data;

@Data
public class OrderProductResponseDTO {

    private Integer quantity;
    //Product detail
    private ProductCatalogResponseDTO productDetail;
}
