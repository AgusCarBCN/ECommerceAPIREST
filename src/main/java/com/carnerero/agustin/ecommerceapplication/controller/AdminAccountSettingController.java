package com.carnerero.agustin.ecommerceapplication.controller;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.SuspensionRequestDTO;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserAccountSettingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/")
public class AdminAccountSettingController {

    private final UserAccountSettingService useCase;
    // Hard delete .Permanent delete from database
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {
        useCase.deleteAccount(userId);
        return ResponseEntity.noContent().build();
    }
    // Suspend user account by admin
    @PatchMapping("/{userId}/suspend")
    public ResponseEntity<Void> suspendUserById(@PathVariable Long userId,
                                                @RequestBody SuspensionRequestDTO reason
                                                ) {
        useCase.suspendAccount(userId,reason.getReason());
        return ResponseEntity.noContent().build();
    }
    // Activate user account by admin
    @PatchMapping("/{userId}/activate")
    public ResponseEntity<Void> activatedUserById(@PathVariable Long userId) {
        useCase.activateAccount(userId);
        return ResponseEntity.noContent().build();
    }
}
