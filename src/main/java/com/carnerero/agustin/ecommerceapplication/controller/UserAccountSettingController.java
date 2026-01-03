package com.carnerero.agustin.ecommerceapplication.controller;


import com.carnerero.agustin.ecommerceapplication.dtos.requests.SuspensionRequestDTO;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserAccountSettingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user/")
public class UserAccountSettingController {

    private final UserAccountSettingService useCase;
    // Soft delete by user
    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<Void> deactivateUserById(@PathVariable Long userId,
                                                   @RequestBody SuspensionRequestDTO reason
    ) {
        useCase.deactivateAccount(userId,reason.getReason());
        return ResponseEntity.noContent().build();
    }
    // Activate user account by user
    @PatchMapping("/{userId}/activate")
    public ResponseEntity<Void> activatedUserById(@PathVariable Long userId) {
        useCase.activateAccount(userId);
        return ResponseEntity.noContent().build();
    }

}
