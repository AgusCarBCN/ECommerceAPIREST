package com.carnerero.agustin.ecommerceapplication.dtos.requests;

import com.carnerero.agustin.ecommerceapplication.model.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequestDTO {
    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

}
