package com.carnerero.agustin.ecommerceapplication.controller.user;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.SuspensionRequestDTO;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserAccountSettingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminAccountSettingController {

    private final UserAccountSettingService userAccountSettingService;


    /**
     * Suspends a user account temporarily or permanently based on admin decision.
     * <p>
     * The suspension reason is provided in the request body.
     *
     * @param reason the suspension reason wrapped in {@link SuspensionRequestDTO}
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content)
     */
    @PatchMapping("/suspend")
    public ResponseEntity<Void> suspendUserById(
            @RequestBody SuspensionRequestDTO reason,
            @RequestParam Long userId,
            Authentication authentication

    ) {
        String email = authentication.getName();
        userAccountSettingService.suspendAccount(email, reason.getReason(),userId);
        return ResponseEntity.noContent().build();
    }
    /**
     * Reactivate a user account by admin.
     *
     * @param userId the userId reactivated by admin
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content)
     */
    @PatchMapping("/reactivate")
    public ResponseEntity<Void> reactivateUserById(
            @RequestParam Long userId,
            Authentication authentication

    ) {
        String email = authentication.getName();
        userAccountSettingService.reactivateAccount(email,userId);
        return ResponseEntity.noContent().build();
    }


}
