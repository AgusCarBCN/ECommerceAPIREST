package com.carnerero.agustin.ecommerceapplication.controller.user;


import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.enums.Roles;
import com.carnerero.agustin.ecommerceapplication.model.enums.UserStatus;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping(path="/search-users")
@PreAuthorize("hasRole('ADMIN')")
public class UserQueriesController {
    private final UserQueryService useCase;

    // ---------------------------
    // Get User by ID
    // ---------------------------
    @Operation(
            summary = "Get user by ID",
            description = "Retrieve user details by user ID.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping("/id/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
        var user = useCase.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    // ---------------------------
    // Get User by Email
    // ---------------------------
    @Operation(
            summary = "Get user by email",
            description = "Retrieve user details by email.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping("/by-email")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@RequestParam String email) {
        var user = useCase.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    // ---------------------------
    // Get User by Username
    // ---------------------------
    @Operation(
            summary = "Get user by username",
            description = "Retrieve user details by username.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping("/by-username")
    public ResponseEntity<UserResponseDTO> getUserByUserName(@RequestParam String userName) {
        var user = useCase.getUserByUsername(userName);
        return ResponseEntity.ok(user);
    }

    // ---------------------------
    // Get Active Users
    // ---------------------------
    @Operation(
            summary = "Get active users",
            description = "Retrieve paginated list of active users.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping("/active-users")
    public ResponseEntity<PageResponse<UserResponseDTO>> getActiveUsers(
            @RequestParam String field,
            @RequestParam boolean desc,
            @RequestParam Integer page
    ) {
        var userPage = useCase.getActiveUsers(field, desc, page);
        return ResponseEntity.ok(userPage);
    }

    // ---------------------------
    // Get Users by Role
    // ---------------------------
    @Operation(
            summary = "Get users by role",
            description = "Retrieve paginated users with a specific role.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping("/by-role")
    public ResponseEntity<PageResponse<UserResponseDTO>> getUsersByRole(
            @RequestParam String field,
            @RequestParam boolean desc,
            @RequestParam Integer page,
            @RequestParam Roles role
    ) {
        var userPage = useCase.getUsersByRole(field, desc, page, role);
        return ResponseEntity.ok(userPage);
    }

    // ---------------------------
    // Get Users by Status
    // ---------------------------
    @Operation(
            summary = "Get users by status",
            description = "Retrieve paginated users filtered by their status.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @GetMapping("/by-status")
    public ResponseEntity<PageResponse<UserResponseDTO>> getUsersByStatus(
            @RequestParam String field,
            @RequestParam boolean desc,
            @RequestParam Integer page,
            @RequestParam UserStatus status
    ) {
        var userPage = useCase.getUsersByStatus(field, desc, page, status);
        return ResponseEntity.ok(userPage);
    }

    // ---------------------------
    // Get Users by Creation Date
    // ---------------------------
    @Operation(
            summary = "Get users created before a date",
            description = "Retrieve paginated users created before the specified date.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @GetMapping("/by-date/before")
    public ResponseEntity<PageResponse<UserResponseDTO>> getUsersByBeforeDate(
            @RequestParam String field,
            @RequestParam boolean desc,
            @RequestParam Integer page,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate beforeDate
    ) {
        var userPage = useCase.getUsersCreatedBefore(beforeDate, field, desc, page);
        return ResponseEntity.ok(userPage);
    }

    @Operation(
            summary = "Get users created after a date",
            description = "Retrieve paginated users created after the specified date.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @GetMapping("/by-date/after")
    public ResponseEntity<PageResponse<UserResponseDTO>> getUsersByAfterDate(
            @RequestParam String field,
            @RequestParam boolean desc,
            @RequestParam Integer page,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate afterDate
    ) {
        var userPage = useCase.getUsersCreatedAfter(afterDate, field, desc, page);
        return ResponseEntity.ok(userPage);
    }

    @Operation(
            summary = "Get users created on a specific date",
            description = "Retrieve paginated users created exactly on the specified date.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @GetMapping("/by-date/equals")
    public ResponseEntity<PageResponse<UserResponseDTO>> getUsersByDate(
            @RequestParam String field,
            @RequestParam boolean desc,
            @RequestParam Integer page,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        var userPage = useCase.getUsersCreatedEquals(date, field, desc, page);
        return ResponseEntity.ok(userPage);
    }

    @Operation(
            summary = "Get users created between two dates",
            description = "Retrieve paginated users created between startDate and endDate.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @GetMapping("/by-date/between")
    public ResponseEntity<PageResponse<UserResponseDTO>> getUsersBetweenDate(
            @RequestParam String field,
            @RequestParam boolean desc,
            @RequestParam Integer page,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate endDate
    ) {
        var userPage = useCase.getUsersCreatedBetween(startDate, endDate, field, desc, page);
        return ResponseEntity.ok(userPage);
    }


}


