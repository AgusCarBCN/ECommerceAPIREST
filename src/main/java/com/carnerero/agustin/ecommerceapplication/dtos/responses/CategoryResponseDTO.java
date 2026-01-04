package com.carnerero.agustin.ecommerceapplication.dtos.responses;

import com.carnerero.agustin.ecommerceapplication.model.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryResponseDTO {

    private Category category;
    private String description;
}
