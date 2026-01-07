package com.carnerero.agustin.ecommerceapplication.model.entities;

import com.carnerero.agustin.ecommerceapplication.model.enums.BillStatus;
import com.carnerero.agustin.ecommerceapplication.model.enums.OrderStatus;
import com.carnerero.agustin.ecommerceapplication.model.enums.PaymentStatus;
import com.carnerero.agustin.ecommerceapplication.model.enums.ShippingMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE) // builder privado
@NoArgsConstructor
@Slf4j
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
    @SequenceGenerator(name = "orders_seq", sequenceName = "orders_seq", allocationSize = 1)
    private Long id;

    // ---------------------------
    // Relation to User
    // ---------------------------
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    // ---------------------------
    // Bill (Invoice)
    // ---------------------------
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bill", unique = true, nullable = true)
    private BillEntity bill; // Created manually after successful payment

    // ---------------------------
    // Products of the order
    // ---------------------------
    @Setter
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> products = new ArrayList<>();

    // ---------------------------
    // Payment (OneToOne)
    // ---------------------------
    @Setter
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    private PaymentEntity payment; // Created manually when payment starts

    // ---------------------------
    // Order Status and Shipping
    // ---------------------------
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_method", nullable = false)
    private ShippingMethod shippingMethod;

    // ---------------------------
    // Timestamps
    // ---------------------------
    @Setter
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Setter
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    // ---------------------------
    // Lifecycle callbacks
    // ---------------------------
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        status=OrderStatus.CREATED;
        if (shippingMethod == null) shippingMethod = ShippingMethod.STANDARD;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @PostUpdate
    protected void afterUpdate() {
        log.info("Order Status updated: {}", status);
    }

    // ---------------------------
    // Builder without ID
    // ---------------------------
    @Builder(builderMethodName = "orderBuilder")
    private OrderEntity(UserEntity user) {
        this.user = user;
    }

    // ---------------------------
    // Helper methods for products
    // ---------------------------
    public void addProduct(ProductEntity product) {
        if (!products.contains(product)) {
            products.add(product);
            product.setOrder(this); // Ensure bidirectional consistency
        }
    }

    public void removeProduct(ProductEntity product) {
        if (products.contains(product)) {
            products.remove(product);
            product.setOrder(null);
        }
    }

    // ---------------------------
    // Cancel order safely
    // ---------------------------
    public void cancelOrder() {
        this.status = OrderStatus.CANCELLED;

        // Optionally update payment
        if (payment != null) {
            payment.setPaymentStatus(PaymentStatus.CANCELLED);
        }

        // Optionally cancel invoice
        if (bill != null) {
            bill.setStatus(BillStatus.CANCELLED);
        }
    }
    // OrderEntity
    public boolean isCancelableByClient() {
        return status == OrderStatus.CREATED || status == OrderStatus.PENDING_PAYMENT;
    }

    public void cancelByClient() {
        this.status = OrderStatus.CANCELLED;
    }

    public void addToTotalAmount(BigDecimal amount) {
        if (amount == null) throw new IllegalArgumentException("amount cannot be null");
        if (this.totalAmount == null) this.totalAmount = BigDecimal.ZERO;
        this.totalAmount = this.totalAmount.add(amount);
    }

    public void subtractFromTotalAmount(BigDecimal amount) {
        if (amount == null) throw new IllegalArgumentException("amount cannot be null");
        if (this.totalAmount == null) this.totalAmount = BigDecimal.ZERO;
        if (this.totalAmount.compareTo(amount) < 0)
            throw new IllegalStateException("Cannot subtract more than total amount");
        this.totalAmount = this.totalAmount.subtract(amount);
    }



}
