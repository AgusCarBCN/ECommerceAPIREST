package com.carnerero.agustin.ecommerceapplication.config;


import com.carnerero.agustin.ecommerceapplication.model.entities.RoleEntity;
import com.carnerero.agustin.ecommerceapplication.model.entities.UserEntity;
import com.carnerero.agustin.ecommerceapplication.model.enums.Roles;
import com.carnerero.agustin.ecommerceapplication.model.enums.UserStatus;
import com.carnerero.agustin.ecommerceapplication.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Configuration
@AllArgsConstructor
@Slf4j
public class LoadingAdminUser {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Bean
    public CommandLineRunner loadData() {

        return (args) -> {
            //Guarda usuario admin si no existe
            if (!userRepository.existsById(1L)) {
                // save an admin user
                userRepository.save(UserEntity.builder()
                        .name("admin")
                        .surname("admin")
                        .email("admin@ecommerce.com")
                        .taxId("00000")
                        .password(passwordEncoder.encode("admin123"))
                        .status(UserStatus.ACTIVE)
                        .statusDescription("Default admin")
                        .createdAt(LocalDate.now())
                        .updatedAt(LocalDateTime.now())
                        .roles(Set.of(RoleEntity.builder()
                                .id(2L)
                                .role(Roles.ROLE_ADMIN).build()))
                        .build());
                log.info("Admin user has been loaded");
            }else{
                log.info("User default admin loaded");
            }
        };
    }
}

