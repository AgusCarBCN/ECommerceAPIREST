package com.carnerero.agustin.ecommerceapplication.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
@Builder
@Setter
@Getter
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

    @ManyToOne()
    @JoinColumn(name="id_order")
    private OrderEntity order;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="id_product_catalog",unique = true, nullable = false)
    private ProductCatalogEntity productCatalog;


}

