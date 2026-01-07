package com.carnerero.agustin.ecommerceapplication.model.entities;

import jakarta.persistence.*;
import lombok.*;

/*
 * Mapear en Hibernate es hacer que un objeto en memoria represente un registro de la tabla y
 *  viceversa, para que la persistencia sea transparente.
 * mapear básicamente significa establecer una correspondencia o relación entre dos cosas.
 * */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
    private Long id;
    private Integer quantity;

    // Reference to the order (parent)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_order", nullable = false)
    private OrderEntity order;

    // Reference to the catalog product (master data)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_product_catalog", nullable = false)
    private ProductCatalogEntity productCatalog;

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
    public void reduceQuantity(int quantity) {
        if (this.quantity < quantity) {
            throw new IllegalArgumentException("Insufficient stock for product: " + productCatalog.getProductName());
        }
        this.quantity -= quantity;
    }

}

