package com.carnerero.agustin.ecommerceapplication.controller.user;


import com.carnerero.agustin.ecommerceapplication.dtos.requests.UpdateUserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserAddressRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserUpdateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/user/update")
@PreAuthorize("hasRole('USER')")
public class UserUpdateController {

    private final UserUpdateService userUpdateService;

    /**
     * Updates any user fields based on the provided request.
     * <p>
     * Supports partial updates for fields included in {@link UpdateUserRequestDTO}.
     *
     * Example request: PATCH /users/{userId}
     *
     * @param authentication
     * @param request the fields to update wrapped in {@link UpdateUserRequestDTO}
     * @return a {@link ResponseEntity} containing the updated user and HTTP status 200 (OK)
     */
    @PatchMapping()
    public ResponseEntity<UserResponseDTO> updateUser(
            @RequestBody UpdateUserRequestDTO request,
            Authentication authentication
    ) {
        UserResponseDTO updatedUser = userUpdateService.updateUserFields(authentication.getName(), request);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Updates a specific address of the user.
     * <p>
     * This endpoint replaces the address details for the given address ID.
     * PUT is used since the update replaces the resource.
     *
     * Example request: PUT /users/{userId}/addresses/{addressId}
     *
     * @param authentication
     * @param addressId the identifier of the address to update
     * @param request the new address data wrapped in {@link UserAddressRequestDTO}
     * @return a {@link ResponseEntity} containing the updated user and HTTP status 200 (OK)
     */
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<UserResponseDTO> updateUserAddress(
            @PathVariable Long addressId,
            @RequestBody UserAddressRequestDTO request,
            Authentication authentication
    ) {
        UserResponseDTO updatedUser = userUpdateService.updateUserAddress(authentication.getName(), addressId, request);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Updates the user's profile image.
     * <p>
     * Accepts a multipart file upload and updates the user's profile image.
     * Returns the URL of the new image upon success.
     *
     * Example request: PUT /users/{userId}/profile-image
     *
     * @param userId the identifier of the user
     * @param file the profile image file to upload
     * @return a {@link ResponseEntity} containing the URL of the updated profile image
     *         and HTTP status 200 (OK), or 500 if the upload fails
     */
    @PutMapping("/{userId}/profile-image")
    public ResponseEntity<String> updateProfileImage(
            @PathVariable Long userId,
            @RequestParam("image") MultipartFile file
    ) {
        try {
            byte[] imageData = file.getBytes();
            String imageType = file.getContentType();
            String imageUrl = userUpdateService.updateProfileImage(userId, imageData, imageType);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image");
        }
    }

}
