package com.carnerero.agustin.ecommerceapplication.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDTO {

    private Integer quantity;
    //Product detail
    private ProductCatalogResponseDTO productDetail;


}
