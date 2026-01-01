package com.carnerero.agustin.ecommerceapplication.services.interfaces.user;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;

/**
 * Servicio para la gestión de la cuenta de usuario.
 * Operaciones como activación, desactivación, eliminación y estado.
 */
public interface UserAccountService {

    /**
     * Activa la cuenta de un usuario
     *
     * @param userId ID del usuario
     * @return true si se activó correctamente
     * //* @throws ResourceNotFoundException Si el usuario no existe
     */
    UserResponseDTO activateAccount(Long userId);

    /**
     * Desactiva temporalmente la cuenta de un usuario
     *
     * @param userId ID del usuario
     * @param reason Razón de la desactivación
     * @return true si se desactivó correctamente
     */
    UserResponseDTO deactivateAccount(Long userId, String reason);

    /**
     * Suspende permanentemente la cuenta de un usuario
     *
     * @param userId ID del usuario
     * @param reason Razón de la suspensión
     * @return true si se suspendió correctamente
     */
    UserResponseDTO suspendAccount(Long userId, String reason);

    /**
     * Reactiva una cuenta previamente desactivada
     *
     * @param userId ID del usuario
     * @return true si se reactivó correctamente
     */
    UserResponseDTO reactivateAccount(Long userId);

    /**
     * Elimina permanentemente la cuenta de un usuario (soft delete)
     *
     * @param userId          ID del usuario
     * @param confirmPassword Contraseña de confirmación
     * @return true si se eliminó correctamente
     * // * @throws BusinessException Si la contraseña es incorrecta
     */
    UserResponseDTO deleteAccount(Long userId, String confirmPassword);

    /**
     * Elimina permanentemente la cuenta (hard delete - solo admin)
     *
     * @param userId  ID del usuario
     * @param adminId ID del administrador que realiza la acción
     * @param reason  Razón de la eliminación
     */
    void permanentlyDeleteAccount(Long userId, Long adminId, String reason);

    /**
     * Obtiene el estado actual de la cuenta
     *
     * @param userId ID del usuario
     * @return Estado de la cuenta
     */
    //UserAccountStatusResponse getAccountStatus(Long userId);

    /**
     * Verifica si la cuenta está activa
     *
     * @param userId ID del usuario
     * @return true si la cuenta está activa
     */
    boolean isAccountActive(Long userId);

    /**
     * Verifica si la cuenta está verificada por email
     *
     * @param userId ID del usuario
     * @return true si la cuenta está verificada
     */
    boolean isAccountVerified(Long userId);


}
