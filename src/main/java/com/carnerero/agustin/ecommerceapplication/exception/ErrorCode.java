package com.carnerero.agustin.ecommerceapplication.exception;

public enum ErrorCode {
    USER_IS_DEACTIVATE("User has been deactivated"),
    USER_IS_ACTIVATE("User has been activated"),
    USER_IS_SUSPEND("User has been suspende because "),
    ADMIN_AUTHORIZE("Only an admin user can "),
    USER_DEACTIVATED_REASON("User deactivated because"),
    INVALID_CREDENTIALS_PASSWORD("Invalid password"),
    INVALID_CREDENTIALS_EMAIL("Invalid email"),
    USER_NOT_FOUND("User not found"),
    ADDRESS_NOT_FOUND("Address not found"),
    ORDER_NOT_FOUND("Order not found"),
    PRODUCT_NOT_FOUND("Product not found in catalog"),
    PAYMENT_NOT_FOUND("Payment not found"),
    PAYMENT_IS_PENDING_REFUND("Payment is yet pending to refund"),
    PAYMENT_IS_REFUND("Payment has already been refunded"),
    PAYMENT_IS_CANCEL_BEFORE("This payment has already been cancelled"),
    PAYMENT_CANNOT_REFUND("Payment cannot refund if is "),
    PAYMENT_CANNOT_CANCEL("Payment cannot cancel if is "),
    ORDER_CANNOT_CANCEL("You cannot cancel this order"),
    ORDER_CANNOT_MODIFY("You cannot modify order in status "),
    ORDER_PAID("Order already paid"),
    ORDER_NOT_PAYABLE("Order not payable with status "),
    EMPTY_ORDER("Order must contain at least one product"),
    TOO_MANY_ITEMS("Cannot remove more items than ordered"),
    EMAIL_ALREADY_EXISTS("Email already registered"),
    INVALID_FIELD("Invalid field"),
    INVALID_REFRESH_TOKEN("Invalid refresh token");

    private final String defaultMessage;

    ErrorCode(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
