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
    private List<ProductRequestDTO> productsToAdd;
    private List<ProductRequestDTO> productsToRemove;
}
