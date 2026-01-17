package com.carnerero.agustin.ecommerceapplication.model.entities;

import com.carnerero.agustin.ecommerceapplication.exception.BusinessException;
import com.carnerero.agustin.ecommerceapplication.exception.ErrorCode;
import com.carnerero.agustin.ecommerceapplication.model.enums.PaymentMethod;
import com.carnerero.agustin.ecommerceapplication.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payments_seq")
    @SequenceGenerator(name = "payments_seq", sequenceName = "payments_seq", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "order_id", unique = true, nullable = false)
    private OrderEntity order;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (paymentStatus == null) paymentStatus = PaymentStatus.PENDING;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ---------------------------
    // Helper method to cancel payment
    // ---------------------------
    public void cancelPayment() {

        if (this.paymentStatus == PaymentStatus.CANCELLED) {
            throw new BusinessException(ErrorCode.PAYMENT_IS_CANCEL_BEFORE.name(),
                    ErrorCode.PAYMENT_IS_CANCEL_BEFORE.getDefaultMessage(),
                    HttpStatus.CONFLICT);
        }

        if (this.paymentStatus == PaymentStatus.SUCCESS) {
            throw new BusinessException(ErrorCode.PAYMENT_CANNOT_CANCEL.name(),
                    ErrorCode.PAYMENT_CANNOT_CANCEL.getDefaultMessage()+this.paymentStatus,
                    HttpStatus.CONFLICT);
        }

        if (this.paymentStatus == PaymentStatus.FAILED) {
            throw new BusinessException(ErrorCode.PAYMENT_CANNOT_CANCEL.name(),
                    ErrorCode.PAYMENT_CANNOT_CANCEL.getDefaultMessage()+this.paymentStatus,
                    HttpStatus.CONFLICT);
        }

        this.paymentStatus = PaymentStatus.CANCELLED;

    }


    // Optional: mark as refunded
    public void refundPayment() {
        if (this.paymentStatus == PaymentStatus.REFUNDED) {
            throw new BusinessException(ErrorCode.PAYMENT_IS_REFUND.name(),
                    ErrorCode.PAYMENT_IS_REFUND.getDefaultMessage(),
                    HttpStatus.CONFLICT);
        }

        if (this.paymentStatus == PaymentStatus.PENDING) {
            throw new BusinessException(ErrorCode.PAYMENT_CANNOT_REFUND.getDefaultMessage(),
                    ErrorCode.PAYMENT_CANNOT_REFUND.getDefaultMessage()+this.paymentStatus,
                    HttpStatus.CONFLICT);
        }

        if (this.paymentStatus == PaymentStatus.FAILED) {
            throw new BusinessException(ErrorCode.PAYMENT_CANNOT_REFUND.getDefaultMessage(),
                    ErrorCode.PAYMENT_CANNOT_REFUND.getDefaultMessage()+this.paymentStatus,
                    HttpStatus.CONFLICT);
        }

        this.paymentStatus = PaymentStatus.REFUNDED;
    }

    // Optional: mark as successful
    public void completePayment() {
        this.paymentStatus = PaymentStatus.SUCCESS;
    }

    // Optional: mark as failed
    public void failPayment() {
        this.paymentStatus = PaymentStatus.FAILED;
    }

    public void calculateAmount(){

    }

}
