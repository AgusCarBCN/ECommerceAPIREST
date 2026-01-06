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
@RequestMapping("/admin")
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
            @RequestBody SuspensionRequestDTO reason
    ) {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        userAccountSettingService.deactivateAccount(email, reason.getReason());
        return ResponseEntity.noContent().build();
    }

    /**
     * Activates a previously deactivated or suspended user account.
     * <p>
     * Only admin users should perform this action.
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content)
     */
    @PatchMapping("/activate")
    public ResponseEntity<Void> activatedUserById() {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        userAccountSettingService.activateAccount(email);
        return ResponseEntity.noContent().build();
    }

}
