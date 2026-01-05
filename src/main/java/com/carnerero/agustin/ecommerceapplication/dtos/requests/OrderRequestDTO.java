package com.carnerero.agustin.ecommerceapplication.dtos.requests;

import com.carnerero.agustin.ecommerceapplication.model.enums.PaymentMethod;
import com.carnerero.agustin.ecommerceapplication.model.enums.ShippingMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequestDTO {

    @NotNull(message="User is required")
    private LoginRequestDTO user;

    @NotNull(message="Shipping method is required")
    private ShippingMethod shippingMethod;

    @NotNull(message="Tax id is required")
    private String taxId;

    @NotEmpty(message = "Order must contain at least one product")
    private Set<ProductRequestDTO> products;

    @NotNull(message = "Payment method is required")
    private PaymentRequestDTO paymentMethod;

}
