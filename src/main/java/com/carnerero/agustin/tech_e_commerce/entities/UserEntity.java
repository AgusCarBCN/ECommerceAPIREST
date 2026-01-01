package com.carnerero.agustin.tech_e_commerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.CreationTimestamp;

import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String userName;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;
    //Reemplaza @PrePersist para que Hibernate se encargue automáticamente de asignar createdAt.

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    //Evita cargar todas las órdenes al traer un usuario, mejor para rendimiento en listas grandes.
    //Inicializa lista para evitar NullPointerException.
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orders = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;

    }
}
