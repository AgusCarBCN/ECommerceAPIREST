package com.carnerero.agustin.ecommerceapplication.dtos.responses;

import com.carnerero.agustin.ecommerceapplication.model.enums.PaymentMethod;
import com.carnerero.agustin.ecommerceapplication.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentResponseDTO {
    private Long orderId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;
}
