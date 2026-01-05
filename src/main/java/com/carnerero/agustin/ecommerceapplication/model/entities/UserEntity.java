package com.carnerero.agustin.ecommerceapplication.model.entities;

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

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Entity
@Builder
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(
            name = "user_seq",        // Alias interno en JPA
            sequenceName = "users_seq", // Nombre de la secuencia real en la DB
            allocationSize = 1         // Incremento de la secuencia
    )
    private Long id;

    @Column(name = "username",  nullable = false, length = 50)
    private String userName;
    @Column(name = "surname",  nullable = false, length = 200)
    private String surname;
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;


    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;
    @Column(name = "updated_at", updatable = false)
    private LocalDateTime updatedAt;

    //Evita cargar todas las órdenes al traer un usuario, mejor para rendimiento en listas grandes.
    //Inicializa lista para evitar NullPointerException.
    @Enumerated(EnumType.STRING)
    @Column(name ="status", nullable = false)
    private UserStatus status;
    @Column(name ="status_description", nullable = false)
    private String statusDescription;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orders = new ArrayList<>();

    // 📍 Direcciones (1 usuario → muchas direcciones)
    @EqualsAndHashCode.Exclude// Evita que la lista genere recursión infinita
    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    private Set<UserAddressEntity> addresses = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        LocalDate now = LocalDate.now();
        LocalDateTime nowTime = LocalDateTime.now();
        status = UserStatus.ACTIVE;
        statusDescription="User active";
        createdAt = now;
        updatedAt=nowTime;
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    @PostUpdate
    protected void afterUpdate() {
        log.info("Order Status updated with status: {}", status);
    }
    public void addAddressSafe(UserAddressEntity address) {
        if (!addresses.contains(address)) {
            addresses.add(address);
        }
        if (address.getUser() != this) {
            address.setUser(this); // asigna el lado dueño sin llamar recursivamente
        }
    }


}
