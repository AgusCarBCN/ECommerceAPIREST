package com.carnerero.agustin.ecommerceapplication.repository;


import com.carnerero.agustin.ecommerceapplication.model.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    PaymentEntity findByOrderId(Long orderId);
}
