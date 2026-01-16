package com.carnerero.agustin.ecommerceapplication.controller.user;


import com.carnerero.agustin.ecommerceapplication.dtos.requests.AuthRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.TokenRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.AuthResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.RefreshTokenEntity;
import com.carnerero.agustin.ecommerceapplication.security.JwtService;
import com.carnerero.agustin.ecommerceapplication.security.UserDetailImpl;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserRegistrationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth") // centralizamos todos los endpoints de autenticación
public class UserRegistrationController {

    private final UserRegistrationService registrationService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    // ------------------- REGISTRO DE USUARIO -------------------
    @PostMapping("/register/user")
    @PreAuthorize("permitAll()")
    public ResponseEntity<AuthResponse> registerUser(
            @RequestBody @Valid UserRequestDTO userRequestDTO
    ) {
        // Crear usuario
        registrationService.registerUser(userRequestDTO);

        // Autenticación inmediata
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequestDTO.getEmail(),
                        userRequestDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Generar tokens
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        // Devolver respuesta con tokens
        AuthResponse response = AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userName(userDetails.getUsername())
                .roles(userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ------------------- LOGIN -------------------
    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Generar tokens
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        AuthResponse response = AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userName(userDetails.getUsername())
                .roles(userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .build();

        return ResponseEntity.ok(response);
    }

    // ------------------- LOGIN ADMIN -------------------
    @PostMapping("/register/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponse> registerAdmin(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        registrationService.registerAdminUser(userRequestDTO);

        // Generar tokens igual que usuario normal
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequestDTO.getEmail(),
                        userRequestDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        AuthResponse response = AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userName(userDetails.getUsername())
                .roles(userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ------------------- REFRESH TOKEN -------------------
    @PostMapping("/refresh-token")
    @PreAuthorize("permitAll()")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody TokenRequestDTO request) {

        String requestRefreshToken = request.getToken();

        RefreshTokenEntity refreshTokenEntity = jwtService.findRefreshToken(request);

        // Generar nuevo access token
        UserDetails userDetails = userDetailsService.loadUserByUsername(refreshTokenEntity.getUser().getEmail());
        String newAccessToken = jwtService.generateAccessToken(userDetails);

        AuthResponse response = AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(requestRefreshToken) // se mantiene el mismo refresh token
                .userName(userDetails.getUsername())
                .roles(userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .build();

        return ResponseEntity.ok(response);
    }
}
