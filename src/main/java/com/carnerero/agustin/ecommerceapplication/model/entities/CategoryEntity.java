package com.carnerero.agustin.ecommerceapplication.model.entities;


import com.carnerero.agustin.ecommerceapplication.model.enums.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categories_seq")
    @SequenceGenerator(name = "categories_seq", sequenceName = "categories_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(length = 500)
    private String description;
    //fetch = FetchType.LAZY para evitar traer todos los productos cada vez.
    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private Set<ProductCatalogEntity> products = new HashSet<>();

    // Métodos de conveniencia para sincronización Many-to-Many
    public void addProduct(ProductCatalogEntity product) {
        products.add(product);
        product.getCategories().add(this);
    }

    public void removeProduct(ProductCatalogEntity product) {
        products.remove(product);
        product.getCategories().remove(this);
    }
}

