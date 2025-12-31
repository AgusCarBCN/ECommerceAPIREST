package com.carnerero.agustin.tech_e_commerce.dtos.requests;

import com.carnerero.agustin.tech_e_commerce.entities.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequestDTO {
    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

}
