package com.carnerero.agustin.ecommerceapplication.dtos.requests;

import com.carnerero.agustin.ecommerceapplication.model.enums.ShippingMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequestDTO {

    @NotNull(message="shipping method is required")
    private ShippingMethod shippingMethod;
    @NotNull(message="Tax id is required")
    private String tax_id;
}
