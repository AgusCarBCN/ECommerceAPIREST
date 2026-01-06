package com.carnerero.agustin.ecommerceapplication.security;

import com.carnerero.agustin.ecommerceapplication.model.entities.UserEntity;
import com.carnerero.agustin.ecommerceapplication.model.enums.UserStatus;
import lombok.*;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@AllArgsConstructor
@Setter
@Getter
public class UserDetailsImpl implements UserDetails {

    /**
     * Implementa UserDetails para que Spring Security pueda utilizar esta clase
     * para autenticar y autorizar usuarios en la aplicación.
     * La clase UserDetailsImpl sirve como puente entre tu entidad User en la base de datos y Spring Security.
     */
    private final UserEntity user; // usa tu User de entity

    @NullMarked
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().toString()))
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }
    @NonNull
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus() == UserStatus.ACTIVE;

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getStatus() != UserStatus.SUSPENDED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}