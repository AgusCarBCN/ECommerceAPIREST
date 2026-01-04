package com.carnerero.agustin.ecommerceapplication.services.interfaces.user;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;

/**
 * Service responsible for managing user account settings.
 * Includes operations such as activation, deactivation, suspension, deletion,
 * and account status verification.
 */
public interface UserAccountSettingService {

    /**
     * Activates a user's account.
     *
     * @param userId the user identifier
     * @return the updated user information after activation
     */
    UserResponseDTO activateAccount(Long userId);

    /**
     * Temporarily deactivates a user's account.
     *
     * @param userId the user identifier
     * @param reason the reason for deactivation
     * @return the updated user information after deactivation
     */
    UserResponseDTO deactivateAccount(Long userId, String reason);

    /**
     * Permanently suspends a user's account.
     *
     * @param userId the user identifier
     * @param reason the reason for suspension
     * @return the updated user information after suspension
     */
    UserResponseDTO suspendAccount(Long userId, String reason);

    /**
     * Permanently deletes a user's account (hard delete – admin only).
     *
     * @param userId the user identifier
     */
    void deleteAccount(Long userId);

    /**
     * Checks whether the user's account is currently active.
     *
     * @param userId the user identifier
     * @return true if the account is active, false otherwise
     */
    boolean isAccountActive(Long userId);

    /**
     * Checks whether the user's account has been verified via email.
     *
     * @param userId the user identifier
     * @return true if the account is verified, false otherwise
     */
    boolean isAccountVerified(Long userId);

    /**
     * Deletes the user's profile image.
     *
     * @param userId the user identifier
     */
    void deleteProfileImage(Long userId);
}
