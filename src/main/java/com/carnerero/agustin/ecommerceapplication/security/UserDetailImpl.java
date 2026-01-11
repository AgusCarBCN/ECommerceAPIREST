package com.carnerero.agustin.ecommerceapplication.security;

import com.carnerero.agustin.ecommerceapplication.model.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
@Slf4j
@NullMarked
@Builder
@Component
@AllArgsConstructor
public class UserDetailImpl implements UserDetails {
    private UserEntity user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<? extends GrantedAuthority> authorities =
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRole().toString())) // USER, ADMIN
                        .toList();

        // Debug usando log
        authorities.forEach(a -> log.info("ROL CARGADO: {}", a.getAuthority()));

        return authorities;

    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
