package com.carnerero.agustin.ecommerceapplication.services.interfaces.user;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.UpdateUserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserAddressRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;

/**
 * Servicio para actualizar datos de usuario
 */
public interface UserUpdateService {


    /**
     * Actualiza dirección de usuario
     *
     * @param userId ID del usuario
     * @param addressId ID de la dirección a cambiar
     * @param  request dirección
     * @return Perfil actualizado
     */

    UserResponseDTO updateUserAddress(Long userId,
                                      Long addressId,
                                      UserAddressRequestDTO request);

    /**
     * Actualiza información de contacto del usuario
     *
     * @param userId ID del usuario
     * @param request datos a actualizar
     * @return Perfil actualizado
     */

    UserResponseDTO updateUserFields(Long userId, UpdateUserRequestDTO request);


    /**
     * Actualiza la imagen de perfil del usuario
     *
     * @param userId ID del usuario
     * @param imageData Datos de la imagen (base64 o bytes)
     * @param imageType Tipo de imagen (jpg, png, etc.)
     * @return URL de la nueva imagen
     */
    String updateProfileImage(Long userId, byte[] imageData, String imageType);




}