package com.carnerero.agustin.ecommerceapplication.controller.user;


import com.carnerero.agustin.ecommerceapplication.dtos.requests.SuspensionRequestDTO;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserAccountSettingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @param userId the identifier of the user performing the deactivation
     * @param reason the reason for deactivation wrapped in {@link SuspensionRequestDTO}
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content)
     */
    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<Void> deactivateUserById(
            @PathVariable Long userId,
            @RequestBody SuspensionRequestDTO reason
    ) {
        useAccountSetting.deactivateAccount(userId, reason.getReason());
        return ResponseEntity.noContent().build();
    }

    /**
     * Activates the user's own account.
     * <p>
     * This operation allows a user to reactivate their previously deactivated account.
     *
     * Example request: PATCH /users/{userId}/activate
     *
     * @param userId the identifier of the user performing the activation
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content)
     */
    @PatchMapping("/{userId}/activate")
    public ResponseEntity<Void> activatedUserById(@PathVariable Long userId) {
        useAccountSetting.activateAccount(userId);
        return ResponseEntity.noContent().build();
    }


}
