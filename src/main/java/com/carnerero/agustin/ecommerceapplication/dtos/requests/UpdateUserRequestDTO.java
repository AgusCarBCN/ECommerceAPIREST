package com.carnerero.agustin.ecommerceapplication.dtos.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserRequestDTO {

    private String name;
    private String surname;
    @Email(message = "Email must be a valid email address")
    private String email;
    @Size(min=8)
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number and one special character"
    )
    private String password;
    @JsonIgnore
    private String profileImage; // opcional: url/base64 si quieres
}

