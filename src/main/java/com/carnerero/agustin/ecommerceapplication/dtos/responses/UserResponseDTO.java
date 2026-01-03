package com.carnerero.agustin.ecommerceapplication.dtos.responses;

import com.carnerero.agustin.ecommerceapplication.model.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String userName;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private UserStatus status;
    private String statusDescription;
    // ✅ Relación 1-N Roles
    private Set<RolesDTO> roles;
    // ✅ Relación 1-N addresses
    private Set<UserAddressResponseDTO> addresses;
}
