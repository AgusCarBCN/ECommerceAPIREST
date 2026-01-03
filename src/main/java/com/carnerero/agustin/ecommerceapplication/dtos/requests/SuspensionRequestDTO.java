package com.carnerero.agustin.ecommerceapplication.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SuspensionRequestDTO {
    private String reason;
}
