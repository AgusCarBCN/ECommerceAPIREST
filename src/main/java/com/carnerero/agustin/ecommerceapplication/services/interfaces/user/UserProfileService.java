package com.carnerero.agustin.ecommerceapplication.services.interfaces.user;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;

import java.util.List;

/**
 * Servicio para la gestión de perfiles de usuario.
 * Operaciones relacionadas con la información personal del usuario.
 */
public interface UserProfileService {

    UserResponseDTO updateBasicProfile(Long userId, UserRequestDTO request);

    /**
     * Actualiza información de contacto del usuario
     *
     * @param userId ID del usuario
     * @param phoneNumber Nuevo número de teléfono
     * @param address Nueva dirección
     * @return Perfil actualizado
     */
    UserResponseDTO updateContactInfo(Long userId, String phoneNumber, String address);

    /**
     * Obtiene el avatar/foto de perfil del usuario
     *
     * @param userId ID del usuario
     * @return URL o path de la imagen de perfil
     */
    String getUserProfileImage(Long userId);

    /**
     * Actualiza la imagen de perfil del usuario
     *
     * @param userId ID del usuario
     * @param imageData Datos de la imagen (base64 o bytes)
     * @param imageType Tipo de imagen (jpg, png, etc.)
     * @return URL de la nueva imagen
     */
    String updateProfileImage(Long userId, byte[] imageData, String imageType);

    /**
     * Elimina la imagen de perfil del usuario
     *
     * @param userId ID del usuario
     */
    void deleteProfileImage(Long userId);

    /**
     * Obtiene la biografía/descripción del usuario
     *
     * @param userId ID del usuario
     * @return Biografía del usuario
     */
    String getUserBio(Long userId);

    /**
     * Actualiza la biografía del usuario
     *
     * @param userId ID del usuario
     * @param bio Nueva biografía
     * @return Biografía actualizada
     */
    String updateUserBio(Long userId, String bio);
}