package com.carnerero.agustin.ecommerceapplication.dtos.responses;


import com.carnerero.agustin.ecommerceapplication.model.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String userName;
    private List<String> roles;
}
