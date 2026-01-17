package com.carnerero.agustin.ecommerceapplication.model.enums;


public enum PaymentStatus {
    PENDING,    // Payment has been initiated but not yet confirmed
    SUCCESS,    // Payment was completed successfully
    FAILED,     // Payment attempt failed
    CANCELLED,  // Payment was cancelled by the user
    REFUNDED ,   // Payment has been refunded (partially or fully)
    REFUND_PENDING//Refund payment has been initiated but not yet confirmed
}
