package com.carnerero.agustin.ecommerceapplication.dtos.requests;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SuspensionRequestDTO {
    private String reason;
}
