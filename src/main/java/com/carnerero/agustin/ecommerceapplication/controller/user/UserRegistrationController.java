package com.carnerero.agustin.ecommerceapplication.controller.user;


import com.carnerero.agustin.ecommerceapplication.dtos.requests.AuthRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.AuthResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.security.JwtService;
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
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping
@PreAuthorize("permitAll()")
public class UserRegistrationController {

    private UserRegistrationService registrationService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    /**
     * Registers a new user in the system.
     * <p>
     * This endpoint creates a standard user account and returns
     * the created user's details along with relevant information.
     *
     * Example request: POST /users/user
     *
     * @param userRequestDTO the registration data for the user
     * @return a {@link ResponseEntity} containing the created user and HTTP status 201 (Created)
     */
    @PostMapping("/register/user")
    public ResponseEntity<UserResponseDTO> registerUser(
            @RequestBody UserRequestDTO userRequestDTO
    ) {
        UserResponseDTO response = registrationService.registerUser(userRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

       /**
       * POST /api/auth/login
       * Authenticate a user and return user info (optionally token)
       * @param request DTO containing username/email and password
       * @return AuthResponseDTO with user info and optional token
       */
      @PostMapping("/login")
      public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequestDTO request) {
          // 1️⃣ Autenticación del usuario
          Authentication authentication = authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(
                          request.getEmail(),
                          request.getPassword()
                  )
          );

          // 2️⃣ Guardar en el contexto de seguridad
          SecurityContextHolder.getContext().setAuthentication(authentication);

          // 3️⃣ Generar token JWT
          UserDetails userDetails = (UserDetails) authentication.getPrincipal();
          String jwt = jwtService.generateToken(userDetails);

          // 4️⃣ Crear response DTO
          AuthResponse response = AuthResponse.builder()
                  .token(jwt)
                  .userName(userDetails.getUsername())
                  .roles(userDetails.getAuthorities().stream()
                          .map(GrantedAuthority::getAuthority)
                          .collect(Collectors.toList())).build();


          // 5️⃣ Devolver respuesta
          return ResponseEntity.ok(response);
      }


    /**
     * Registers a new admin user in the system.
     * <p>
     * This endpoint creates an administrator account. Only authorized clients
     * should have access to register an admin user.
     *
     * Example request: POST /users/admin
     *
     * @param userRequestDTO the registration data for the admin user
     * @return a {@link ResponseEntity} containing the created admin user and HTTP status 201 (Created)
     */
    @PostMapping("/register/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> registerAdmin(
            @RequestBody UserRequestDTO userRequestDTO
    ) {
        UserResponseDTO response = registrationService.registerAdminUser(userRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

}
