package com.carnerero.agustin.ecommerceapplication.services.interfaces.user;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.UpdateUserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserAddressRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;

/**
 * Service responsible for updating user data.
 */
public interface UserUpdateService {

    /**
     * Updates a user's address.
     *
     * @param userId the user identifier
     * @param addressId the identifier of the address to update
     * @param request the new address data
     * @return the updated user profile
     */
    UserResponseDTO updateUserAddress(
            Long userId,
            Long addressId,
            UserAddressRequestDTO request
    );

    /**
     * Updates user profile fields (contact and personal information).
     *
     * @param userId the user identifier
     * @param request the fields to be updated
     * @return the updated user profile
     */
    UserResponseDTO updateUserFields(
            Long userId,
            UpdateUserRequestDTO request
    );

    /**
     * Updates the user's profile image.
     *
     * @param userId the user identifier
     * @param imageData the image data (bytes or Base64-decoded bytes)
     * @param imageType the image type (jpg, png, etc.)
     * @return the URL of the updated profile image
     */
    String updateProfileImage(
            Long userId,
            byte[] imageData,
            String imageType
    );
}
