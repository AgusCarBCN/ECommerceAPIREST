package com.carnerero.agustin.ecommerceapplication.repository;

import com.carnerero.agustin.ecommerceapplication.model.entities.CategoryEntity;
import com.carnerero.agustin.ecommerceapplication.model.entities.ProductCatalogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for managing.
 * Provides read and write operations for products, including
 * category-based filtering, pagination, and search capabilities.
 */
public interface ProductCatalogRepository extends JpaRepository<ProductCatalogEntity, UUID> {

    /**
     * Retrieves a product by its unique identifier.
     *
     * @param id the product UUID
     * @return an Optional containing the product if found
     */
    Optional<ProductCatalogEntity> findById(UUID id);



    /**
     * Retrieves a paginated list of products associated with a given category ID.
     * <p>
     * A product may belong to multiple categories.
     * Uses DISTINCT to avoid duplicate products caused by the many-to-many relationship.
     *
     * @param categoryId the category identifier
     * @param pageable pagination and sorting information
     * @return a page of products associated with the given category
     */
    @Query("""
           select distinct p
           from ProductCatalogEntity p
           join p.categories c
           where c.id = :categoryId
           """)
    Page<ProductCatalogEntity> findByCategoryId(
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );

    /**
     * Retrieves a paginated list of products whose price is between the given range.
     *
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @param pageable pagination and sorting information
     * @return a page of products within the specified price range
     */
    Page<ProductCatalogEntity> findByPriceBetween(
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    );

    /**
     * Retrieves a paginated list of products whose name contains the given text,
     * ignoring case sensitivity.
     *
     * @param name the text to search for in the product name
     * @param pageable pagination and sorting information
     * @return a page of matching products
     */
    Page<ProductCatalogEntity> findByProductNameContainingIgnoreCase(
            String name,
            Pageable pageable
    );

    /**
     * Retrieves a paginated list of products that belong to the given category enum.
     *
     * @param category the category enum value
     * @param pageable pagination and sorting information
     * @return a page of products associated with the category
     */
    @Query("""
           select distinct p
           from ProductCatalogEntity p
           join p.categories c
           where c.category = :category
           """)
    Page<ProductCatalogEntity> findByCategory(
            @Param("category") CategoryEntity category,
            Pageable pageable
    );
}
