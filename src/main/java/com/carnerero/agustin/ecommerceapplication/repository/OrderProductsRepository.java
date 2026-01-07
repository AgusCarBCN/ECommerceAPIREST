package com.carnerero.agustin.ecommerceapplication.repository;

import com.carnerero.agustin.ecommerceapplication.model.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderProductsRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByOrderId(Long orderId);

}

