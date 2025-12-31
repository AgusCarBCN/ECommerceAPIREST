package com.carnerero.agustin.tech_e_commerce.repositories;

import com.carnerero.agustin.tech_e_commerce.entities.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, Long> {
}
