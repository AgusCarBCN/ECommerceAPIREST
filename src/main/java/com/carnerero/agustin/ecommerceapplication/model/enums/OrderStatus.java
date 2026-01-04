package com.carnerero.agustin.ecommerceapplication.model.enums;

public enum OrderStatus {
    CREATED,           // The order has been created but not yet processed
    PAID,              // Payment has been successfully received
    CANCELLED,         // The order has been cancelled by the user or system
    PROCESSING,        // The order is being prepared or processed for shipment
    SHIPPED,           // The order has been shipped to the customer
    DELIVERED,         // The order has been delivered to the customer
    PAYMENT_PENDING,   // Payment is awaiting confirmation (e.g., bank transfer)
    PAYMENT_FAILED,    // Payment attempt has failed (card declined, etc.)
    REFUNDED,          // The payment has been refunded to the customer
    ON_HOLD,           // The order is on hold due to some issue (e.g., stock problem)
    RETURNED,          // The customer has returned the order
    FAILED             // The order has failed due to an unexpected error
}
