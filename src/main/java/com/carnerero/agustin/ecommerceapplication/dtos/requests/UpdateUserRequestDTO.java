package com.carnerero.agustin.ecommerceapplication.dtos.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserRequestDTO {
    private String name;
    private String email;
    private String password;
    @JsonIgnore
    private String profileImage; // opcional: url/base64 si quieres
}

