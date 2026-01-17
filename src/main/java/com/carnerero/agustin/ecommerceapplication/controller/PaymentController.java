package com.carnerero.agustin.ecommerceapplication.controller;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.PaymentRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PaymentResponseDTO;
import com.carnerero.agustin.ecommerceapplication.exception.user.BusinessException;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/payment")

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
    @PostMapping("/{paymentId}/refund")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PaymentResponseDTO> refundPayment(@PathVariable Long paymentId) {
            var response=paymentService.refundPayment(paymentId);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(response);
    }
    @PostMapping("/{paymentId}/confirm-refund")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentResponseDTO> confirmRefundPayment(@PathVariable Long paymentId) {
                   var response=paymentService.confirmRefundPayment(paymentId);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(response);

    }
}
