package com.carnerero.agustin.tech_e_commerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
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
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    //Evita cargar todas las órdenes al traer un usuario, mejor para rendimiento en listas grandes.
    //Inicializa lista para evitar NullPointerException.
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orders = new ArrayList<>();

    /*@OneToMany(mappedBy = "user",
               fetch =  FetchType.EAGER,
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<OrderEntity> orders=new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }*/

}
