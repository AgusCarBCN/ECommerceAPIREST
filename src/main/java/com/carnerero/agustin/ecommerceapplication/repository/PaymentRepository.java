package com.carnerero.agustin.ecommerceapplication.repository;


import com.carnerero.agustin.ecommerceapplication.model.entities.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    PaymentEntity findByOrderId(Long orderId);
    @Query("""
    select p
    from PaymentEntity p
    join p.order o
    join o.user u
    where u.email = :email
""")
    Page<PaymentEntity> findPaymentsByUserEmail(@Param("email") String email, PageRequest pageRequest);

}
