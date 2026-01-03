package com.carnerero.agustin.ecommerceapplication.repository;

import com.carnerero.agustin.ecommerceapplication.model.entities.OrderProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface OrderProductsRepository extends JpaRepository<OrderProductsEntity, Long> {
    List<OrderProductsEntity> findByOrderId(Long orderId);
}

