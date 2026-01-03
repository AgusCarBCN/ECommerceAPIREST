package com.carnerero.agustin.ecommerceapplication.services.interfaces.user;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;

/**
 * Servicio para la gestión de la cuenta de usuario.
 * Operaciones como activación, desactivación, eliminación y estado.
 */
public interface UserAccountSettingService {

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
     * Elimina permanentemente la cuenta (hard delete - solo admin)
     *
     * @param userId  ID del usuario

     */
    void deleteAccount(Long userId);

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
     * @param userId ID del usuario
     * @return true si la cuenta está verificada
     */


    boolean isAccountVerified(Long userId);
    /**
     * Elimina la imagen de perfil del usuario
     *
     * @param userId ID del usuario
     */
    void deleteProfileImage(Long userId);

}
