package com.carnerero.agustin.ecommerceapplication.dtos.responses;

import lombok.Data;

@Data
public class OrderProductResponseDTO {

    private Long quantity;
    //Product detail
    private ProductCatalogResponseDTO productDetail;
}
