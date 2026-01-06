package com.carnerero.agustin.ecommerceapplication.controller.user;


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
@RequestMapping(path="/search/users")
public class UserQueriesController {
    private final UserQueryService useCase;

    /**
     * Retrieves a user by their unique identifier.
     *
     * Example request: GET /users/id/{userId}
     *
     * @param userId the identifier of the user
     * @return a {@link ResponseEntity} containing the user details and HTTP status 200 (OK)
     */
    @GetMapping("/id/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
        var user = useCase.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    /**
     * Retrieves a user by their email address.
     *
     * Example request: GET /users/by-email?email=user@example.com
     *
     * @param email the user's email
     * @return a {@link ResponseEntity} containing the user details and HTTP status 200 (OK)
     */
    @GetMapping("/by-email")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@RequestParam String email) {
        var user = useCase.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    /**
     * Retrieves a user by their username.
     *
     * Example request: GET /users/by-username?userName=johndoe
     *
     * @param userName the user's username
     * @return a {@link ResponseEntity} containing the user details and HTTP status 200 (OK)
     */
    @GetMapping("/by-username")
    public ResponseEntity<UserResponseDTO> getUserByUserName(@RequestParam String userName) {
        var user = useCase.getUserByUsername(userName);
        return ResponseEntity.ok(user);
    }

    /**
     * Retrieves a paginated list of active users.
     *
     * Example request: GET /users/active-users?field=createdAt&desc=true&page=0
     *
     * @param field the field used for sorting
     * @param desc true for descending order, false for ascending
     * @param page the page number to retrieve (0-based)
     * @return a {@link ResponseEntity} containing a paginated response of active users
     */
    @GetMapping("/active-users")
    public ResponseEntity<PageResponse<UserResponseDTO>> getActiveUsers(
            @RequestParam String field,
            @RequestParam boolean desc,
            @RequestParam Integer page
    ) {
        var userPage = useCase.getActiveUsers(field, desc, page);
        return ResponseEntity.ok(userPage);
    }

    /**
     * Retrieves a paginated list of users by role.
     *
     * Example request: GET /users/by-role?role=ADMIN&field=username&desc=false&page=0
     *
     * @param field the field used for sorting
     * @param desc true for descending order, false for ascending
     * @param page the page number to retrieve (0-based)
     * @param role the role to filter users by
     * @return a {@link ResponseEntity} containing a paginated response of users with the given role
     */
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

    /**
     * Retrieves a paginated list of users by account status.
     *
     * Example request: GET /users/by-status?status=ACTIVE&field=username&desc=true&page=0
     *
     * @param field the field used for sorting
     * @param desc true for descending order, false for ascending
     * @param page the page number to retrieve (0-based)
     * @param status the account status to filter users by
     * @return a {@link ResponseEntity} containing a paginated response of users with the given status
     */
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

    /**
     * Retrieves a paginated list of users created before a specified date.
     *
     * Example request: GET /users/by-date/before?beforeDate=2026-01-01&field=createdAt&desc=false&page=0
     *
     * @param field the field used for sorting
     * @param desc true for descending order, false for ascending
     * @param page the page number to retrieve (0-based)
     * @param beforeDate the date to filter users created before
     * @return a {@link ResponseEntity} containing a paginated response of users
     */
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

    /**
     * Retrieves a paginated list of users created after a specified date.
     *
     * Example request: GET /users/by-date/after?afterDate=2026-01-01&field=createdAt&desc=false&page=0
     *
     * @param field the field used for sorting
     * @param desc true for descending order, false for ascending
     * @param page the page number to retrieve (0-based)
     * @param afterDate the date to filter users created after
     * @return a {@link ResponseEntity} containing a paginated response of users
     */
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

    /**
     * Retrieves a paginated list of users created on a specific date.
     *
     * Example request: GET /users/by-date/equals?date=2026-01-01&field=createdAt&desc=false&page=0
     *
     * @param field the field used for sorting
     * @param desc true for descending order, false for ascending
     * @param page the page number to retrieve (0-based)
     * @param date the date to filter users created on
     * @return a {@link ResponseEntity} containing a paginated response of users
     */
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

    /**
     * Retrieves a paginated list of users created between two specified dates.
     *
     * Example request: GET /users/by-date/between?startDate=2026-01-01&endDate=2026-01-31&field=createdAt&desc=false&page=0
     *
     * @param field the field used for sorting
     * @param desc true for descending order, false for ascending
     * @param page the page number to retrieve (0-based)
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a {@link ResponseEntity} containing a paginated response of users
     */
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


