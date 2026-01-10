package com.carnerero.agustin.ecommerceapplication.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products_catalog")
public class ProductCatalogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false, length = 200)
    private String productName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price = BigDecimal.ZERO;

    @Column(
            name = "discount_price",
            precision = 10,
            scale = 2,
            insertable = false,
            updatable = false
    )
    private BigDecimal discountPrice;

    @Column(nullable = false)
    private BigDecimal discountPercentage = BigDecimal.ZERO;

    @Column(name="stock_quantity",nullable = false)
    private Integer stockQuantity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<CategoryEntity> categories = new HashSet<>();


    // Métodos de dominio para sincronizar Many-to-Many
    public void addCategory(CategoryEntity category) {
        categories.add(category);
        category.getProducts().add(this);
    }

    public void removeCategory(CategoryEntity category) {
        categories.remove(category);
        category.getProducts().remove(this);
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void restoreStock(int quantity) {
        this.stockQuantity += quantity;
    }
    public void reduceStock(int quantity) {
        if (this.stockQuantity < quantity) {
            throw new IllegalArgumentException("Insufficient stock for product: " + this.productName);
        }
        this.stockQuantity -= quantity;
    }
}
