package com.carnerero.agustin.ecommerceapplication.repository;

import com.carnerero.agustin.ecommerceapplication.model.entities.RefreshTokenEntity;
import com.carnerero.agustin.ecommerceapplication.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByUser(UserEntity user);
}

