package com.carnerero.agustin.ecommerceapplication.services.interfaces.user;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.exception.user.UserExistException;

public interface UserRegistrationService {

    /**
     * Registra un nuevo usuario en el sistema
     *
     * @param request Datos de registro del usuario
     * @return Respuesta con el usuario creado y token de confirmación
     * @throws UserExistException Si el usuario ya existe en el sistema
     */
    UserResponseDTO registerUser(UserRequestDTO request);
    /**
     * Registra un nuevo usuario administrador en el sistema
     *
     * @param request Datos de registro del usuario
     * @return Respuesta con el usuario creado y token de confirmación
     * @throws UserExistException Si el usuario ya existe en el sistema
     */
    UserResponseDTO registerAdminUser(UserRequestDTO request);


    /**
     * Reenvía el email de verificación
     *
     * @param email Email del usuario
     * @return true si se reenvió correctamente
     */
    boolean resendVerificationEmail(String email);

    /**
     * Confirma la cuenta del usuario mediante token
     *
     * @param verificationToken Token de verificación
     * @return true si la cuenta fue confirmada
     */
    boolean confirmUserAccount(String verificationToken);

}