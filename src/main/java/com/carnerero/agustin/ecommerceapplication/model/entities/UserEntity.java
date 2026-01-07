package com.carnerero.agustin.ecommerceapplication.model.entities;

import com.carnerero.agustin.ecommerceapplication.model.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Slf4j
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(
            name = "user_seq",
            sequenceName = "users_seq",
            allocationSize = 1
    )

    private Long id;

    @Column(name = "username", nullable = false, length = 50)
    private String name;

    @Column(name = "surname", nullable = false, length = 200)
    private String surname;

    @Column(name = "tax_id", unique = true, nullable = false, length = 20)
    private String taxId;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ---------------------------
    // Status for soft delete / audit
    // ---------------------------

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status;

    @Column(name = "status_description", nullable = false)
    private String statusDescription;

    // ---------------------------
    // Orders (1 user -> many orders)
    // ---------------------------
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<OrderEntity> orders = new ArrayList<>();

    // ---------------------------
    // Addresses (1 user -> many addresses)
    // ---------------------------
    @EqualsAndHashCode.Exclude
    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<UserAddressEntity> addresses = new HashSet<>();

    // ---------------------------
    // Roles (ManyToMany)
    // ---------------------------
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    // ---------------------------
    // Lifecycle callbacks
    // ---------------------------
    @PrePersist
    protected void onCreate() {
        LocalDate nowDate = LocalDate.now();
        LocalDateTime nowTime = LocalDateTime.now();
        createdAt = nowDate;
        updatedAt = nowTime;

        // Default status = ACTIVE
        status = UserStatus.ACTIVE;
        statusDescription = "User active";
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @PostUpdate
    protected void afterUpdate() {
        log.info("User status updated: {}", status);
    }

    // ---------------------------
    // Utility methods
    // ---------------------------
    public void addAddressSafe(UserAddressEntity address) {
        if (!addresses.contains(address)) {
            addresses.add(address);
        }
        if (address.getUser() != this) {
            address.setUser(this); // asigna el lado dueño
        }
    }

    public void deactivateUser() {
        status = UserStatus.DEACTIVATED;
        statusDescription = "User deactivated";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<? extends GrantedAuthority> authorities =
                getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRole().toString())) // USER, ADMIN
                        .toList();

        // Debug usando log
        authorities.forEach(a -> log.info("ROL CARGADO: {}", a.getAuthority()));

        return authorities;

    }
    @Transient // evita que JPA lo considere
    @JsonIgnore // evita que MapStruct y Jackson lo usen
    @Override
    public String getUsername() {
        return this.email;
    }


}
