package com.carnerero.agustin.ecommerceapplication.controller.user;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.SuspensionRequestDTO;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserAccountSettingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminAccountSettingController {

    private final UserAccountSettingService useCase;

    /**
     * Permanently deletes a user account from the database (hard delete).
     * <p>
     * Only admin users should have access to this operation.
     *
     * @param userId the identifier of the user to delete
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content)
     */
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {
        useCase.deleteAccount(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Suspends a user account temporarily or permanently based on admin decision.
     * <p>
     * The suspension reason is provided in the request body.
     *
     * @param userId the identifier of the user to suspend
     * @param reason the suspension reason wrapped in {@link SuspensionRequestDTO}
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content)
     */
    @PatchMapping("/{userId}/suspend")
    public ResponseEntity<Void> suspendUserById(
            @PathVariable Long userId,
            @RequestBody SuspensionRequestDTO reason
    ) {
        useCase.suspendAccount(userId, reason.getReason());
        return ResponseEntity.noContent().build();
    }

    /**
     * Activates a previously deactivated or suspended user account.
     * <p>
     * Only admin users should perform this action.
     *
     * @param userId the identifier of the user to activate
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content)
     */
    @PatchMapping("/{userId}/activate")
    public ResponseEntity<Void> activatedUserById(@PathVariable Long userId) {
        useCase.activateAccount(userId);
        return ResponseEntity.noContent().build();
    }

}
