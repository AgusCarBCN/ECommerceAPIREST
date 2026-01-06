package com.carnerero.agustin.ecommerceapplication.controller.user;


import com.carnerero.agustin.ecommerceapplication.dtos.requests.SuspensionRequestDTO;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserAccountSettingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserAccountSettingController {

    private final UserAccountSettingService useAccountSetting;

    /**
     * Deactivates the user's own account (soft delete).
     * <p>
     * This operation allows a user to temporarily deactivate their account.
     * The reason for deactivation is provided in the request body.
     *
     * Example request: PATCH /users/{userId}/deactivate
     *

     * @param reason the reason for deactivation wrapped in {@link SuspensionRequestDTO}
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content)
     */
    @PatchMapping("/deactivate")
    public ResponseEntity<Void> deactivateUser(@RequestBody SuspensionRequestDTO reason) {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        useAccountSetting.deactivateAccount(email, reason.getReason());
        return ResponseEntity.noContent().build();
    }

    /**
     * Activates the user's own account.
     * <p>
     * This operation allows a user to reactivate their previously deactivated account.
     *
     * Example request: PATCH /users/{userId}/activate
     *
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content)
     */
    @PatchMapping("/activate")
    public ResponseEntity<Void> activatedUserById() {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        useAccountSetting.activateAccount(email);
        return ResponseEntity.noContent().build();
    }


}
