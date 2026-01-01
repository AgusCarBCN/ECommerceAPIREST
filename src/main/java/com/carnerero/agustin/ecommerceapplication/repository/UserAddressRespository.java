package com.carnerero.agustin.ecommerceapplication.repository;

import com.carnerero.agustin.ecommerceapplication.model.entities.UserAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRespository extends JpaRepository<UserAddressEntity, Long> {
}
