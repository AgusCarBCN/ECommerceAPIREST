package com.carnerero.agustin.ecommerceapplication.controller;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.PaymentRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PaymentResponseDTO;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PaymentResponseDTO> createPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {

        var response=paymentService.createPayment(paymentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping("/all")
    public ResponseEntity<PageResponse<PaymentResponseDTO>> getAllPayments(@RequestParam String field,
                                                                           @RequestParam boolean desc,
                                                                           @RequestParam Integer page,
                                                                           Authentication authentication) {
        var response=paymentService.getUserPayments(authentication.getName(),field,desc,page);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
    @Operation(
            summary = "Request payment refund",
            description = """
                Marks a successful payment as REFUND_PENDING.
                Only payments with status SUCCESS can be refunded.
                """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Refund initiated and pending to confirm."),
            @ApiResponse(responseCode = "400", description = "Invalid payment state"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "500", description = "Cannot refund payment if is REFUNDED")
    })
    @PostMapping("/{paymentId}/refund")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> refundPayment(@PathVariable Long paymentId) {
            var response=paymentService.refundPayment(paymentId);
            return ResponseEntity.ok(response);
    }
    @PostMapping("/{paymentId}/confirm-refund")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> confirmRefundPayment(@PathVariable Long paymentId) {
                   var response=paymentService.confirmRefundPayment(paymentId);
            return ResponseEntity.ok(response);


    }
}
