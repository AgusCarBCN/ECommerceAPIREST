package com.carnerero.agustin.tech_e_commerce.dtos.requests;

import com.carnerero.agustin.tech_e_commerce.entities.ShippingMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequestDTO {

    @NotNull(message="shipping method is required")
    private ShippingMethod shippingMethod;
    @NotNull(message="Tax id is required")
    private String tax_id;
}
