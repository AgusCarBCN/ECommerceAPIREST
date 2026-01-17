package com.carnerero.agustin.ecommerceapplication.controller;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.PaymentRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PaymentResponseDTO;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/payment")
@Tag(
        name = "Payments",
        description = "Payment management and refund operations"
)
public class PaymentController {

    private final PaymentService paymentService;

    // ---------------------------
    // Create Payment
    // ---------------------------
    @Operation(
            summary = "Create a new payment",
            description = "Creates a payment for a user's order. Returns payment details.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or order has no products"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PaymentResponseDTO> createPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {

        var response=paymentService.createPayment(paymentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // ---------------------------
    // Get All Payments for User
    // ---------------------------
    @Operation(
            summary = "Get all payments for a user",
            description = "Returns paginated payments for the authenticated user.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping("/all")
    public ResponseEntity<PageResponse<PaymentResponseDTO>> getAllPayments(@RequestParam String field,
                                                                           @RequestParam boolean desc,
                                                                           @RequestParam Integer page,
                                                                           Authentication authentication) {
        var response=paymentService.getUserPayments(authentication.getName(),field,desc,page);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
    // ---------------------------
    // Request Refund
    // ---------------------------
    @Operation(
            summary = "Request payment refund",
            description = """
                Marks a successful payment as REFUND_PENDING.
                Only payments with status SUCCESS can be refunded.
                """,
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Refund initiated and pending to confirm."),
            @ApiResponse(responseCode = "400", description = "Invalid payment state"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "409", description = "Cannot refund payment if already refunded"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @PostMapping("/{paymentId}/refund")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> refundPayment(@PathVariable Long paymentId) {
            var response=paymentService.refundPayment(paymentId);
            return ResponseEntity.ok(response);
    }

    // ---------------------------
    // Confirm Refund (Admin)
    // ---------------------------
    @Operation(
            summary = "Confirm a payment refund",
            description = """
                Confirms a payment refund. 
                Only payments with status REFUND_PENDING can be confirmed.
                Restores product stock and cancels the order.
                """,
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Refund confirmed and order canceled."),
            @ApiResponse(responseCode = "404", description = "Payment or Order not found"),
            @ApiResponse(responseCode = "409", description = "Cannot confirm refund if payment is not pending"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @PostMapping("/{paymentId}/confirm-refund")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> confirmRefundPayment(@PathVariable Long paymentId) {
                   var response=paymentService.confirmRefundPayment(paymentId);
            return ResponseEntity.ok(response);
    }
}
