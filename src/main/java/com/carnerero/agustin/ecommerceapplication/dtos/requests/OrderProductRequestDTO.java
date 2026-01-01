package com.carnerero.agustin.ecommerceapplication.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderProductRequestDTO {

    @NotNull(message="Product catalog id is required")
    private UUID productCatalogId;
    @NotBlank(message="Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

}
