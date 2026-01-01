package com.carnerero.agustin.ecommerceapplication.services.interfaces.user;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;

public interface UserRegistrationService {

    /**
     * Registra un nuevo usuario en el sistema
     *
     * @param request Datos de registro del usuario
     * @return Respuesta con el usuario creado y token de confirmación
     //* @throws BusinessException Si el usuario ya existe o hay error de negocio
     //* @throws ValidationException Si los datos no son válidos
     */
    UserResponseDTO registerUser(UserRequestDTO request);

    /**
     * Verifica si un email puede ser usado para registro
     *
     * @param email Email a verificar
     * @return true si el email está disponible
     */
    boolean isEmailAvailable(String email);

    /**
     * Verifica si un username puede ser usado para registro
     *
     * @param username Username a verificar
     * @return true si el username está disponible
     */
    boolean isUsernameAvailable(String username);

    /**
     * Valida los datos de registro antes de procesarlos
     *
     * @param request Datos a validar
     */
    void validateRegistrationData(UserRequestDTO request);

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