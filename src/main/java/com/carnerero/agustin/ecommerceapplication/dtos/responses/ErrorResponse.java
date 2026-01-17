package com.carnerero.agustin.ecommerceapplication.dtos.responses;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {

    @Schema(example = "PAYMENT_NOT_FOUND")
    private String code;

    @Schema(example = "Payment not found")
    private String message;

    @Schema(example = "2026-01-17T12:30:00")
    private LocalDateTime timestamp;

    @Schema(example = "/api/payments/42/refund")
    private String path;
}

