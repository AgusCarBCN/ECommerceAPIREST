package com.carnerero.agustin.ecommerceapplication.model.entities;

import com.carnerero.agustin.ecommerceapplication.model.enums.OrderStatus;
import com.carnerero.agustin.ecommerceapplication.model.enums.ShippingMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)// builder privado
@NoArgsConstructor
@Slf4j
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
    @SequenceGenerator(name = "orders_seq", sequenceName = "orders_seq", allocationSize = 1)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_bill", unique = true, nullable = false)
    private BillEntity bill;

    @Setter
    @OneToMany(mappedBy = "order",
            fetch =  FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ProductEntity> products=new ArrayList<>();

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_method", nullable = false)
    private ShippingMethod shippingMethod;
    @Setter
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;

        if (status == null) status = OrderStatus.CREATED;
        if (shippingMethod == null) shippingMethod = ShippingMethod.STANDARD;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @PostUpdate
    protected void afterUpdate() {
        log.info("Order Status updated with status: {}", status);
    }

    // --------- Builder sin id ---------
    @Builder(builderMethodName = "orderBuilder")
    private OrderEntity(UserEntity user,
                        BillEntity bill) {
        this.user = user;
        this.bill = bill;
    }
}
