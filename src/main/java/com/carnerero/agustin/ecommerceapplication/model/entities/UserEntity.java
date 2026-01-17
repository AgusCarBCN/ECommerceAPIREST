package com.carnerero.agustin.ecommerceapplication.model.entities;

import com.carnerero.agustin.ecommerceapplication.model.enums.Roles;
import com.carnerero.agustin.ecommerceapplication.model.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Slf4j
@Entity
@Table(name = "users")
public class UserEntity  {

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
    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<OrderEntity> orders = new ArrayList<>();

    // ---------------------------
    // Addresses (1 user -> many addresses)
    // ---------------------------
    @EqualsAndHashCode.Exclude
    @Builder.Default
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
    @Builder.Default
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
        addresses.add(address);
        if (address.getUser() != this) {
            address.setUser(this); // asigna el lado dueño
        }
    }
    public void addRolesToUser(boolean isAdmin){
        Set<RoleEntity>userRoles = new HashSet<>();
        if(isAdmin){
            var adminRol=(RoleEntity.builder()
                    .id(2L)
                    .role(Roles.ROLE_ADMIN)
                    .build());
            userRoles.add(adminRol);
        }
        //Asignar rol de usuario
        RoleEntity userRol=RoleEntity.builder()
                .id(1L)
                .role(Roles.ROLE_USER)
                .build();
       userRoles.add(userRol);
        this.roles=userRoles;
    }
    public void encodePassword(String password) {
        var  passwordEncoder =new BCryptPasswordEncoder();
        this.password= passwordEncoder.encode(password);
    }
    public void deactivateUser(String reason) {
        if(this.status==UserStatus.DEACTIVATED){
            throw new IllegalStateException("User has been deactivated");
        }

        this.status = UserStatus.DEACTIVATED;
        this.statusDescription = "User deactivated because "+reason;
        this.updatedAt = LocalDateTime.now();
    }
    public void activateUser(){
        if(this.status==UserStatus.ACTIVE){
            throw new IllegalStateException("User has been activated");
        }
        if(this.status==UserStatus.SUSPENDED){
            throw new IllegalStateException("Only an admin user can activate this account");
        }
        this.status = UserStatus.ACTIVE;
        this.statusDescription = "User active";
        this.updatedAt = LocalDateTime.now();
    }
    public void suspendUser(){
        if(this.status==UserStatus.SUSPENDED){
            throw new IllegalStateException("User has been suspended");
        }
        this.status = UserStatus.SUSPENDED;
        this.statusDescription = "User suspended";
        this.updatedAt = LocalDateTime.now();
    }
    public void reactivateUser(){
        if(this.status==UserStatus.ACTIVE){
            throw  new IllegalStateException("User has been activated");
        }
        this.status = UserStatus.ACTIVE;
        this.statusDescription = "User active";
        this.updatedAt = LocalDateTime.now();
    }
  }
