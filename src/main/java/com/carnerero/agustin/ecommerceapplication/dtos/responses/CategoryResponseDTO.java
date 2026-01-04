package com.carnerero.agustin.ecommerceapplication.dtos.responses;

import com.carnerero.agustin.ecommerceapplication.model.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryResponseDTO {

    private Category category;
    private String description;
}
