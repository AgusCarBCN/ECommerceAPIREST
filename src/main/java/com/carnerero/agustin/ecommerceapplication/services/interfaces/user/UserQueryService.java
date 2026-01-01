package com.carnerero.agustin.ecommerceapplication.services.interfaces.user;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/**
 * Servicio para consultas y búsquedas de usuarios.
 * Solo operaciones de lectura sin efectos secundarios.
 */


public interface UserQueryService {

    /**
     * Obtiene un usuario por ID
     *
     * @param userId ID del usuario
     * @return Usuario encontrado
     */
    UserResponseDTO getUserById(Long userId);

    /**
     * Obtiene un usuario por email
     *
     * @param email Email del usuario
     * @return Usuario encontrado
     */
    UserResponseDTO getUserByEmail(String email);

    /**
     * Obtiene un usuario por username
     *
     * @param username Nombre de usuario
     * @return Usuario encontrado
     */
    UserResponseDTO getUserByUsername(String username);

    /**
     * Busca usuarios por rol
     *
     * @param roleName Nombre del rol
     * @param pageable Configuración de paginación
     * @return Página de usuarios con ese rol
     */
    PageResponse<UserResponseDTO> getUsersByRole(String roleName, Pageable pageable);

    /**
     * Obtiene usuarios activos
     *
     * @param pageable Configuración de paginación
     * @return Página de usuarios activos
     */
    PageResponse<UserResponseDTO> getActiveUsers(Pageable pageable);

    /**
     * Obtiene usuarios registrados en un rango de fechas
     *
     * @param startDate Fecha de inicio
     * @param endDate   Fecha de fin
     * @param pageable  Configuración de paginación
     * @return Página de usuarios
     */
    PageResponse<UserResponseDTO> getUsersByRegistrationDate(
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );

    /**
     * Verifica si un email existe en el sistema
     *
     * @param email Email a verificar
     * @return true si el email existe
     */
    boolean existsByEmail(String email);

    /**
     * Verifica si un username existe en el sistema
     *
     * @param username Username a verificar
     * @return true si el username existe
     */
    boolean existsByUsername(String username);

    /**
     * Obtiene el conteo total de usuarios
     *
     * @return Número total de usuarios
     */
    long countAllUsers();

    /**
     * Obtiene el conteo de usuarios por estado
     *
     * @param active Estado de actividad
     * @return Número de usuarios con ese estado
     */
    long countUsersByStatus(boolean active);


}
