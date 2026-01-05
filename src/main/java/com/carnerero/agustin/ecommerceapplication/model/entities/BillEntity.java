package com.carnerero.agustin.ecommerceapplication.model.entities;

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

    @Column(nullable = false, length = 50)
    private String taxId;

    @Column(nullable = false)
    private BigDecimal  totalAmount;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }


    // Método de conveniencia para enlazar con una orden
    public void setOrder(OrderEntity order) {
        this.order = order;
        if (order.getBill() != this) {
            order.setBill(this);
        }
    }
}
