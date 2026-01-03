package com.carnerero.agustin.ecommerceapplication.controller;


import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/register")
public class UserRegistrationController {

    private UserRegistrationService useCase;


    // Register user
    @PostMapping("/user")
    public ResponseEntity<UserResponseDTO> registerUser(
            @RequestBody UserRequestDTO userRequestDTO
    ){
        UserResponseDTO response = useCase.registerUser(userRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Register admin
    @PostMapping("/admin")
    public ResponseEntity<UserResponseDTO> registerAdmin(
            @RequestBody UserRequestDTO userRequestDTO
    ){
        UserResponseDTO response = useCase.registerAdminUser(userRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
