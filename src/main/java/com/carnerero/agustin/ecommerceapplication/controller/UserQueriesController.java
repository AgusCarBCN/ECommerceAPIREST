package com.carnerero.agustin.ecommerceapplication.controller;


import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.enums.Roles;
import com.carnerero.agustin.ecommerceapplication.model.enums.UserStatus;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserQueryService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/search/users")
public class UserQueriesController {
    private final UserQueryService useCase
            ;

    // Get user by id
    @GetMapping("/id/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable Long userId
    ) {
            var user = useCase.getUserById(userId);
            return ResponseEntity.ok(user);
    }
    // Get user by email
    @GetMapping("/by-email")
    public ResponseEntity<UserResponseDTO> getUserByEmail(
            @RequestParam String email
    ) {
            var user = useCase.getUserByEmail(email);
            return ResponseEntity.ok(user);
    }
    // Get user by email
    @GetMapping("/by-username")
    public ResponseEntity<UserResponseDTO> getUserByUserName(
            @RequestParam String userName
    ) {
            var user = useCase.getUserByUsername(userName);
            return ResponseEntity.ok(user);
    }
    @GetMapping("/active-users")
    public ResponseEntity<PageResponse<UserResponseDTO>> getActiveUsers(
            @RequestParam String field,
            @RequestParam boolean desc,
            @RequestParam Integer page
    ) {
            var userPage = useCase.getActiveUsers(field,desc,page);
            return ResponseEntity.ok(userPage);
    }
    @GetMapping("/by-role")
    public ResponseEntity<PageResponse<UserResponseDTO>> getUsersByRole(
            @RequestParam String field,
            @RequestParam boolean desc,
            @RequestParam Integer page,
            @RequestParam Roles role
    ) {
            var userPage = useCase.getUsersByRole(field,desc,page,role);
            return ResponseEntity.ok(userPage);
    }
    @GetMapping("/by-status")
    public ResponseEntity<PageResponse<UserResponseDTO>> getUsersByStatus(
            @RequestParam String field,
            @RequestParam boolean desc,
            @RequestParam Integer page,
            @RequestParam UserStatus status
    ) {
            var userPage = useCase.getUsersByStatus(field,desc,page,status);
            return ResponseEntity.ok(userPage);
    }
    @GetMapping("/by-date/before")
    public ResponseEntity<PageResponse<UserResponseDTO>> getUsersByBeforeDate(
            @RequestParam String field,
            @RequestParam boolean desc,
            @RequestParam Integer page,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam LocalDate beforeDate
            ) {
        var userPage = useCase.getUsersCreatedBefore(beforeDate,field,desc,page);
        return ResponseEntity.ok(userPage);
    }
    @GetMapping("/by-date/after")
    public ResponseEntity<PageResponse<UserResponseDTO>> getUsersByAfterDate(
            @RequestParam String field,
            @RequestParam boolean desc,
            @RequestParam Integer page,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam LocalDate afterDate
    ) {
        var userPage = useCase.getUsersCreatedAfter(afterDate,field,desc,page);
        return ResponseEntity.ok(userPage);
    }
    @GetMapping("/by-date/equals")
    public ResponseEntity<PageResponse<UserResponseDTO>> getUsersByDate(
            @RequestParam String field,
            @RequestParam boolean desc,
            @RequestParam Integer page,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        var userPage = useCase.getUsersCreatedEquals(date,field,desc,page);
        return ResponseEntity.ok(userPage);
    }
    @GetMapping("/by-date/between")
    public ResponseEntity<PageResponse<UserResponseDTO>> getUsersBetweenDate(
            @RequestParam String field,
            @RequestParam boolean desc,
            @RequestParam Integer page,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam LocalDate endDate
    ) {
        var userPage = useCase.getUsersCreatedBetween(startDate,endDate,field,desc,page);
        return ResponseEntity.ok(userPage);
    }
}


