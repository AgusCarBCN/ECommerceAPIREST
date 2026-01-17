package com.carnerero.agustin.ecommerceapplication.controller.user;


import com.carnerero.agustin.ecommerceapplication.dtos.requests.AuthRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.TokenRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.AuthResponse;
import com.carnerero.agustin.ecommerceapplication.model.entities.RefreshTokenEntity;
import com.carnerero.agustin.ecommerceapplication.security.JwtService;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserRegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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


    // ---------------------------
    // Register User
    // ---------------------------
    @Operation(
            summary = "Register a new user",
            description = "Registers a new user and immediately authenticates them, returning access and refresh tokens.",
            security = @SecurityRequirement(name = "")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or email already exists")
    })
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
        assert userDetails != null;
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

    // ---------------------------
    // Login
    // ---------------------------
    @Operation(
            summary = "User login",
            description = "Authenticates a user with email and password, returning access and refresh tokens.",
            security = @SecurityRequirement(name = "")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid email or password")
    })
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
        assert userDetails != null;
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

    // ---------------------------
    // Register Admin
    // ---------------------------
    @Operation(
            summary = "Register a new admin user",
            description = "Registers a new admin user. Only accessible to users with ADMIN role.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Admin user registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or email already exists"),
            @ApiResponse(responseCode = "403", description = "Forbidden: Admin role required")
    })
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
        assert userDetails != null;
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

    // ---------------------------
    // Refresh Token
    // ---------------------------
    @Operation(
            summary = "Refresh access token",
            description = "Generates a new access token using a valid refresh token. The refresh token remains the same.",
            security = @SecurityRequirement(name = "")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access token refreshed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid refresh token"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
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
