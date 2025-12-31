package com.carnerero.agustin.tech_e_commerce.repositories;

import com.carnerero.agustin.tech_e_commerce.entities.ProductCatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ProductCatalogRepository extends JpaRepository<ProductCatalogEntity, UUID> {
}

