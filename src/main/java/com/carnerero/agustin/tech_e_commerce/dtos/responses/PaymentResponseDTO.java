package com.carnerero.agustin.tech_e_commerce.dtos.responses;

import com.carnerero.agustin.tech_e_commerce.entities.PaymentMethod;
import com.carnerero.agustin.tech_e_commerce.entities.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponseDTO {
    private Long orderId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;
}
