package com.carnerero.agustin.ecommerceapplication.repository;

import com.carnerero.agustin.ecommerceapplication.model.entities.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


public interface BillRepository extends JpaRepository<BillEntity, UUID> {
}
