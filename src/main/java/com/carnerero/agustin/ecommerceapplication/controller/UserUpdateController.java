package com.carnerero.agustin.ecommerceapplication.controller;


import com.carnerero.agustin.ecommerceapplication.dtos.requests.UpdateUserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserAddressRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserUpdateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/update/users")
public class UserUpdateController {

    private final UserUpdateService userUpdateService;

    // 🔹 Actualizar cualquier campo del usuario
    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long userId,
            @RequestBody UpdateUserRequestDTO request) {
        UserResponseDTO updatedUser = userUpdateService.updateUserFields(userId, request);
        return ResponseEntity.ok(updatedUser);
    }

    // 🔹 Actualizar dirección (puede seguir siendo PUT)
    @PutMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<UserResponseDTO> updateUserAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId,
            @RequestBody UserAddressRequestDTO request) {
        UserResponseDTO updatedUser = userUpdateService.updateUserAddress(userId, addressId, request);
        return ResponseEntity.ok(updatedUser);
    }

    // 🔹 Actualizar imagen de perfil
    @PutMapping("/{userId}/profile-image")
    public ResponseEntity<String> updateProfileImage(
            @PathVariable Long userId,
            @RequestParam("image") MultipartFile file) {
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
