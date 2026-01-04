package com.carnerero.agustin.ecommerceapplication.dtos.requests;

import com.carnerero.agustin.ecommerceapplication.model.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCategoryRequestDTO {

    @NotNull(message = "Category type is required")
    private Category category;
    @NotBlank(message = "Category description  is required")
    private String description;
}
