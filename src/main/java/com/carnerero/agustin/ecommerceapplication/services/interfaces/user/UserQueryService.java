package com.carnerero.agustin.ecommerceapplication.services.interfaces.user;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.enums.Roles;
import com.carnerero.agustin.ecommerceapplication.model.enums.UserStatus;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
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
     * @param field Ordenación por campo field
     * @param desc tipo de ordenación ,desc true descendente, false ascendente
     * @param numberOfPages número de páginas
     * @param role Nombre del rol
     * @return Página de usuarios con ese rol
     */
    PageResponse<UserResponseDTO> getUsersByRole(String field,
                                                 Boolean desc,
                                                 Integer numberOfPages,
                                                 Roles role);

    /**
     * Busca usuarios por status
     *
     * @param field Ordenación por campo field
     * @param desc tipo de ordenación ,desc true descendente, false ascendente
     * @param numberOfPages número de páginas
     * @param status Nombre del rol
     * @return Página de usuarios con ese status
     */

    PageResponse<UserResponseDTO> getUsersByStatus(String field,
                                                   Boolean desc,
                                                   Integer numberOfPages,
                                                   UserStatus status);


    /**
     * Obtiene usuarios activos
     * @param field Ordenación por campo field
     * @param desc tipo de ordenación ,desc true descendente, false ascendente
     * @param numberOfPages número de páginas
     * @return Página de usuarios activos
     */
    PageResponse<UserResponseDTO> getActiveUsers(String field,
                                                 Boolean desc,
                                                 Integer numberOfPages);

    /**
     * Obtiene usuarios registrados después de una fecha
     *
     * @param date fecha
     * @param field Ordenación por campo field
     * @param desc tipo de ordenación ,desc true descendente, false ascendente
     * @param numberOfPages número de páginas
     * @return Página de usuarios
     */
    PageResponse<UserResponseDTO> getUsersCreatedAfter(
            LocalDate date,
            String field,
            Boolean desc,
            Integer numberOfPages
    );
    /**
     * Obtiene usuarios registrados antes de una fecha
     *
     * @param date fecha
     * @param field Ordenación por campo field
     * @param desc tipo de ordenación ,desc true descendente, false ascendente
     * @param numberOfPages número de páginas
     * @return Página de usuarios
     */
    PageResponse<UserResponseDTO> getUsersCreatedBefore(
            LocalDate date,
            String field,
            Boolean desc,
            Integer numberOfPages
    );
    /**
     * Obtiene usuarios registrados antes de una fecha
     *
     * @param date fecha
     * @param field Ordenación por campo field
     * @param desc tipo de ordenación ,desc true descendente, false ascendente
     * @param numberOfPages número de páginas
     * @return Página de usuarios
     */
    PageResponse<UserResponseDTO> getUsersCreatedEquals(
            LocalDate date,
            String field,
            Boolean desc,
            Integer numberOfPages
    );

    /**
     * Obtiene usuarios registrados entre dos fechas
     *
     * @param startDate fecha
     * @param endDate fecha
     * @param field Ordenación por campo field
     * @param desc tipo de ordenación ,desc true descendente, false ascendente
     * @param numberOfPages número de páginas
     * @return Página de usuarios
     */

    PageResponse<UserResponseDTO> getUsersCreatedBetween(
            LocalDate startDate,
            LocalDate endDate,
            String field,
            Boolean desc,
            Integer numberOfPages
    );

    /**
     * Obtiene el conteo total de usuarios
     *
     * @return Número total de usuarios
     */
    long countAllUsers();

    /**
     * Obtiene el conteo de usuarios por estado
     *
     * @param status Estado de actividad
     * @return Número de usuarios con ese estado
     */
    long countUsersByStatus(UserStatus status);

    /**
     * Obtiene el avatar/foto de perfil del usuario
     *
     * @param userId ID del usuario
     * @return URL o path de la imagen de perfil
     */
    String getUserProfileImage(Long userId);

}
