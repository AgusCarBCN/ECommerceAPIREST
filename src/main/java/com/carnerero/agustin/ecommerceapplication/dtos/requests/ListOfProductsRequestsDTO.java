package com.carnerero.agustin.ecommerceapplication.dtos.requests;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListOfProductsRequestsDTO {
    @NotEmpty(message = "Products list cannot be empty")
    private List<ProductRequestDTO> products;
}
