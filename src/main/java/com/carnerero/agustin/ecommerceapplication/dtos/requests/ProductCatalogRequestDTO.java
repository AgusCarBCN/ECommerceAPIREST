package com.carnerero.agustin.ecommerceapplication.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

//Sólo usado por el administrador para introducir un nuevo producto o modificar catálogo
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductCatalogRequestDTO {
    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 20, message = "Product name must be between 10 and 200 characters")
    private String productName;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Description is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must have up to 2 decimal places")
    private BigDecimal price;
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Long stockQuantity;
    @NotNull(message = "Categories are required")
    @Size(min = 1, message = "At least one category must be assigned")
    private Set<CategoryRequestDTO> categories;

}
