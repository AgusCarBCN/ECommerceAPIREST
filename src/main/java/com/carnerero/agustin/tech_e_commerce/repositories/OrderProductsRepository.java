package com.carnerero.agustin.tech_e_commerce.repositories;

import com.carnerero.agustin.tech_e_commerce.entities.OrderProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductsRepository extends JpaRepository<OrderProductsEntity, Long> {
    List<OrderProductsEntity> findByOrderId(Long orderId);
}

