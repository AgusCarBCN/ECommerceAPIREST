package com.carnerero.agustin.ecommerceapplication.services.interfaces;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.PaymentRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PaymentResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {

    /**
     * Create a payment record for an order.
     *
     * @param paymentRequest DTO with amount, method, etc.
     * @return PaymentResponseDTO
     */
    PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequest);



    /**
     * Retrieve all payments associated with a specific order.
     * @param email the email of the order
     * @return PageResponse of PaymentResponseDTO
     */
    PageResponse<PaymentResponseDTO> getUserPayments(String email,
                                             String field,
                                             Boolean desc,
                                             Integer numberOfPages);

    /**
     * Refund payment by user and  waiting for confirm
     * @param paymentId the id of the payment to refund
     *
     */
    String refundPayment(Long paymentId);

    /**
     * Confirm refund payment
     * @param paymentId
     *
     */
    String confirmRefundPayment(Long paymentId);
}
