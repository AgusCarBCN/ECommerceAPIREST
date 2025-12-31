package com.carnerero.agustin.tech_e_commerce.repositories;

import com.carnerero.agustin.tech_e_commerce.entities.Category;
import com.carnerero.agustin.tech_e_commerce.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByCategory(Category category); // validar duplicados
}

