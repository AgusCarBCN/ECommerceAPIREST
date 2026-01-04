package com.carnerero.agustin.ecommerceapplication.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.UUID;
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
    private BigInteger quantity;
    @ManyToOne()
    @JoinColumn(name="id_order")
    private OrderEntity order;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="id_product_catalog",unique = true, nullable = false)
    private ProductCatalogEntity productCatalog;
}

