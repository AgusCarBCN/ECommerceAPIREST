package com.carnerero.agustin.ecommerceapplication.services.interfaces.user;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.LoginRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.LoginResponseDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.exception.user.UserExistException;

public interface UserRegistrationService {

    /**
     * Registers a new user in the system.
     *
     * @param request the user registration data
     * @return the created user information
     * @throws UserExistException if the user already exists in the system
     */
    UserResponseDTO registerUser(UserRequestDTO request);

    /**
     * Registers a new administrator user in the system.
     *
     * @param request the admin user registration data
     * @return the created admin user information
     * @throws UserExistException if the user already exists in the system
     */
    UserResponseDTO registerAdminUser(UserRequestDTO request);
    /**
     * Login user in the system.
     *
     * @param loginRequest the request data user
     * @return LoginResponse returns user information
     * @throws UserExistException if the user already exists in the system
     */

    public void login(LoginRequestDTO loginRequest);
    /**
     * Resends the account verification email.
     *
     * @param email the user's email address
     * @return true if the verification email was successfully resent
     */

    boolean resendVerificationEmail(String email);

    /**
     * Confirms a user account using a verification token.
     *
     * @param verificationToken the verification token
     * @return true if the account was successfully confirmed
     */
    boolean confirmUserAccount(String verificationToken);
}
