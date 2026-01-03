package com.carnerero.agustin.ecommerceapplication.repository;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.enums.Roles;
import com.carnerero.agustin.ecommerceapplication.model.entities.UserEntity;
import com.carnerero.agustin.ecommerceapplication.model.enums.UserStatus;
import jakarta.persistence.Entity;
import org.hibernate.property.access.internal.PropertyAccessGetterImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /* =========================
       BÚSQUEDAS
       ========================= */

    Optional<UserEntity> findByUserNameIgnoreCase(String userName);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUserNameContainingIgnoreCase(String userName);



    /* =========================
       FILTROS
       ========================= */
    @Query("from UserEntity  where status= 'ACTIVE'")
    Page<UserEntity> findAllActive (Pageable pageable);

    Page<UserEntity> findAllByStatus(UserStatus status,Pageable pageable);

    Page<UserEntity> findAllByCreatedAtAfter(Pageable pageable, LocalDate createdAt);

    Page<UserEntity> findAllByCreatedAtBefore(LocalDate createdAt, Pageable pageable);

    Page<UserEntity> findByCreatedAt(LocalDate createdAt, Pageable pageable);

    Page<UserEntity> findAllByCreatedAtBetween(
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );


    Page<UserEntity> findAllByRoles_Role(Roles role, Pageable pageable);


    /* =========================
       VALIDACIONES
       ========================= */

    boolean existsByUserNameIgnoreCase(String userName);

    boolean existsByEmailIgnoreCase(String email);

    /* =========================
       MÉTRICAS
       ========================= */

    long count();

    long countByStatus(UserStatus status);

    /* =========================
       ELIMINACIÓN
       ========================= */

    @Modifying
    @Transactional
    @Query("DELETE FROM UserEntity u WHERE u.id = :id")
    void deleteByIdDirect(@Param("id") Long id);
}

