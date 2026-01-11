package com.carnerero.agustin.ecommerceapplication.model.enums;

public enum OrderStatus {
    CREATED,        // Order has been created but payment not started yet
    PENDING_PAYMENT,// Order is waiting for payment
    PAID,           // Payment has been successfully completed
    CANCELLED,      // Order has been cancelled by user or due to timeout
    EXPIRED,         // Order was not paid within the allowed time
    REFUNDED
}
