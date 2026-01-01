package com.carnerero.agustin.tech_e_commerce.repositories;

import com.carnerero.agustin.tech_e_commerce.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
}
