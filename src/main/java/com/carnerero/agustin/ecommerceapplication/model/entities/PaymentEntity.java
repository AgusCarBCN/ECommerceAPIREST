package com.carnerero.agustin.ecommerceapplication.model.entities;

import com.carnerero.agustin.ecommerceapplication.model.enums.PaymentMethod;
import com.carnerero.agustin.ecommerceapplication.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

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
        this.paymentStatus = PaymentStatus.CANCELLED;
    }

    // Optional: mark as refunded
    public void refundPayment() {
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
