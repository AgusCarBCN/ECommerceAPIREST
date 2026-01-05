package com.carnerero.agustin.ecommerceapplication.services.interfaces;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.PaymentRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PaymentResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.enums.PaymentStatus;

public interface PaymentService {

    /**
     * Create a payment record for an order.
     *
     * @param paymentRequest DTO with amount, method, etc.
     * @return PaymentResponseDTO
     */
    PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequest);

    /**
     * Update the status of a payment (e.g., PENDING → PAID, FAILED, REFUNDED)
     * @param paymentId ID of the payment
     * @param newStatus new payment status
     * @return updated PaymentResponseDTO
     */
    PaymentResponseDTO updatePaymentStatus(Long paymentId, PaymentStatus newStatus);

    /**
     * Retrieve all payments associated with a specific order.
     * @param orderId ID of the order
     * @return PageResponse of PaymentResponseDTO
     */
    PaymentResponseDTO getPaymentsByOrder(Long orderId);
}
