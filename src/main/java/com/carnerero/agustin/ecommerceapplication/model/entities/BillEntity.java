package com.carnerero.agustin.ecommerceapplication.model.entities;

import com.carnerero.agustin.ecommerceapplication.model.enums.BillStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "bills")
public class BillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;  // ID renombrado correctamente

    @ToString.Exclude
    @OneToOne(mappedBy = "bill", cascade = CascadeType.ALL)
    private OrderEntity order;

    @Setter
    @Column(name = "tax_amount", nullable = false)
    private BigDecimal taxAmount;

    @Setter
    @Column(name = "shipping_amount", nullable = false)
    private BigDecimal shippingAmount;

    @Column(nullable = false)
    private BigDecimal  totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillStatus status;

    private LocalDateTime issuedAt;



    @PrePersist
    protected void onCreate() {

        issuedAt = LocalDateTime.now();
        status=BillStatus.ACTIVE;
    }


    // Método de conveniencia para enlazar con una orden
    public void setOrder(OrderEntity order) {
        this.order = order;
        if (order.getBill() != this) {
            order.setBill(this);
        }
    }
}
