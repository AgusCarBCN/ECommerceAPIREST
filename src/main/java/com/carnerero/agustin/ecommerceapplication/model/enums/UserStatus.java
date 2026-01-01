package com.carnerero.agustin.ecommerceapplication.model.enums;

public enum UserStatus {
    ACTIVE,          // Active and able to use the system
    INACTIVE,        // Not active, but can be reactivated
    PENDING,         // Waiting for email verification or admin approval
    SUSPENDED,       // Temporarily suspended (e.g., for review)
    BANNED,          // Permanently banned
    DELETED,         // Soft deleted
    LOCKED           // Locked due to security reasons (failed login attempts)
}
