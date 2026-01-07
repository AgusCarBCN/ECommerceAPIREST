package com.carnerero.agustin.ecommerceapplication.repository;

import com.carnerero.agustin.ecommerceapplication.model.entities.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


/**
 * Repository interface for accessing Order entities from the database.
 * Extends JpaRepository to provide CRUD operations.
 */
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    /**
     * Retrieve a paginated list of orders for a specific user.
     *
     * @param userId the ID of the user whose orders are being retrieved
     * @param pageable pagination and sorting information
     * @return a Page of OrderEntity objects belonging to the user
     */
    Page<OrderEntity> findByUserId(Long userId, Pageable pageable);

    /**
     * Check if a user has any existing orders.
     *
     * @param userId the ID of the user
     * @return true if the user has at least one order, false otherwise
     */
    boolean existsByUserId(Long userId);

    /**
     * Retrieve a paginated list of orders for a specific user by their email.
     *
     * @param email the email of the user whose orders are being retrieved
     * @param pageable pagination and sorting information
     * @return a Page of OrderEntity objects belonging to the user
     */
    Page<OrderEntity> findByUserEmail(String email, Pageable pageable);

    /**
     * Find an order by its ID, including its associated products and their catalog details.
     *
     * @param orderId the ID of the order to retrieve
     * @return an Optional containing the OrderEntity if found, or empty if not found
     */
    @Query("""
    select o from OrderEntity o
    join fetch o.products p
    join fetch p.productCatalog
    where o.id = :orderId
""")
    Optional<OrderEntity> findByIdWithProducts(Long orderId);
}
