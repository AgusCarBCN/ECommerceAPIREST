package com.carnerero.agustin.ecommerceapplication.controller.user;


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
    @PostMapping("/user")
    public ResponseEntity<UserResponseDTO> registerUser(
            @RequestBody UserRequestDTO userRequestDTO
    ) {
        UserResponseDTO response = useCase.registerUser(userRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
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
    @PostMapping("/admin")
    public ResponseEntity<UserResponseDTO> registerAdmin(
            @RequestBody UserRequestDTO userRequestDTO
    ) {
        UserResponseDTO response = useCase.registerAdminUser(userRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

}
