package com.carnerero.agustin.ecommerceapplication.dtos.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponseDTO {

    private Long userId;
    private String username;
    private String email;
   //private String token; // optional if using JWT
}
