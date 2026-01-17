package com.carnerero.agustin.ecommerceapplication.repository;

import com.carnerero.agustin.ecommerceapplication.model.entities.OrderEntity;
import com.carnerero.agustin.ecommerceapplication.model.entities.RefreshTokenEntity;
import com.carnerero.agustin.ecommerceapplication.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);

    @Query("""
    select rt from RefreshTokenEntity rt
    join fetch rt.user u
    where u.id = :userId
""")
    Optional<RefreshTokenEntity> findByUserId(Long userId);

    @Query("SELECT r FROM RefreshTokenEntity r JOIN FETCH r.user WHERE r.token = :token")
    Optional<RefreshTokenEntity> findByTokenWithUser(@Param("token") String token);

    void deleteByUser(UserEntity user);
}

