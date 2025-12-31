package com.carnerero.agustin.tech_e_commerce.dtos.requests;

import com.carnerero.agustin.tech_e_commerce.entities.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryRequestDTO {
    @NotNull(message = "Category type is required")
    private Category category;
    @NotBlank(message = "Category description  is required")
    private String description;
}
