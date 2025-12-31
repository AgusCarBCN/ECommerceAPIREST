package com.carnerero.agustin.tech_e_commerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_products")
@IdClass(OrderProductsId.class)
public class OrderProductsEntity {

    @Id
    private Long orderId;

    @Id
    private UUID productCatalogId;

    @Column(nullable = false)
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_catalog_id", insertable = false, updatable = false)
    private ProductCatalogEntity productCatalog;

    public OrderProductsEntity(OrderEntity order, ProductCatalogEntity product, Long quantity) {
        this.orderId = order.getId();
        this.productCatalogId = product.getId();
        this.order = order;
        this.productCatalog = product;
        this.quantity = quantity;
    }
}

