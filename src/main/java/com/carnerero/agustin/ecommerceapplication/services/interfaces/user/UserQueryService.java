package com.carnerero.agustin.ecommerceapplication.services.interfaces.user;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.enums.Roles;
import com.carnerero.agustin.ecommerceapplication.model.enums.UserStatus;
import java.time.LocalDate;

/**
 * Service responsible for user queries and searches.
 * Read-only operations with no side effects.
 */
public interface UserQueryService {

    /**
     * Retrieves a user by ID.
     *
     * @param userId the user identifier
     * @return the found user
     */
    UserResponseDTO getUserById(Long userId);

    /**
     * Retrieves a user by email address.
     *
     * @param email the user's email
     * @return the found user
     */
    UserResponseDTO getUserByEmail(String email);

    /**
     * Retrieves a user by username.
     *
     * @param username the user's username
     * @return the found user
     */
    UserResponseDTO getUserByUsername(String username);

    /**
     * Retrieves a paginated list of users by role.
     *
     * @param field the field used for sorting
     * @param desc sorting order: true for descending, false for ascending
     * @param numberOfPages the page number to retrieve
     * @param role the user role
     * @return a paginated response containing users with the specified role
     */
    PageResponse<UserResponseDTO> getUsersByRole(
            String field,
            Boolean desc,
            Integer numberOfPages,
            Roles role
    );

    /**
     * Retrieves a paginated list of users by account status.
     *
     * @param field the field used for sorting
     * @param desc sorting order: true for descending, false for ascending
     * @param numberOfPages the page number to retrieve
     * @param status the user account status
     * @return a paginated response containing users with the specified status
     */
    PageResponse<UserResponseDTO> getUsersByStatus(
            String field,
            Boolean desc,
            Integer numberOfPages,
            UserStatus status
    );

    /**
     * Retrieves a paginated list of active users.
     *
     * @param field the field used for sorting
     * @param desc sorting order: true for descending, false for ascending
     * @param numberOfPages the page number to retrieve
     * @return a paginated response containing active users
     */
    PageResponse<UserResponseDTO> getActiveUsers(
            String field,
            Boolean desc,
            Integer numberOfPages
    );

    /**
     * Retrieves a paginated list of users created after the specified date.
     *
     * @param date the reference date
     * @param field the field used for sorting
     * @param desc sorting order: true for descending, false for ascending
     * @param numberOfPages the page number to retrieve
     * @return a paginated response containing users
     */
    PageResponse<UserResponseDTO> getUsersCreatedAfter(
            LocalDate date,
            String field,
            Boolean desc,
            Integer numberOfPages
    );

    /**
     * Retrieves a paginated list of users created before the specified date.
     *
     * @param date the reference date
     * @param field the field used for sorting
     * @param desc sorting order: true for descending, false for ascending
     * @param numberOfPages the page number to retrieve
     * @return a paginated response containing users
     */
    PageResponse<UserResponseDTO> getUsersCreatedBefore(
            LocalDate date,
            String field,
            Boolean desc,
            Integer numberOfPages
    );

    /**
     * Retrieves a paginated list of users created on the specified date.
     *
     * @param date the reference date
     * @param field the field used for sorting
     * @param desc sorting order: true for descending, false for ascending
     * @param numberOfPages the page number to retrieve
     * @return a paginated response containing users
     */
    PageResponse<UserResponseDTO> getUsersCreatedEquals(
            LocalDate date,
            String field,
            Boolean desc,
            Integer numberOfPages
    );

    /**
     * Retrieves a paginated list of users created between two dates.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param field the field used for sorting
     * @param desc sorting order: true for descending, false for ascending
     * @param numberOfPages the page number to retrieve
     * @return a paginated response containing users
     */
    PageResponse<UserResponseDTO> getUsersCreatedBetween(
            LocalDate startDate,
            LocalDate endDate,
            String field,
            Boolean desc,
            Integer numberOfPages
    );

    /**
     * Returns the total number of users.
     *
     * @return the total user count
     */
    long countAllUsers();

    /**
     * Returns the number of users by account status.
     *
     * @param status the user account status
     * @return the number of users with the given status
     */
    long countUsersByStatus(UserStatus status);

    /**
     * Retrieves the user's profile image (avatar).
     *
     * @param userId the user identifier
     * @return the URL or path of the user's profile image
     */
    String getUserProfileImage(Long userId);
}
