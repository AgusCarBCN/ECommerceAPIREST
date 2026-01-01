package com.carnerero.agustin.ecommerceapplication.services.impl.user;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.UserEntity;
import com.carnerero.agustin.ecommerceapplication.repository.UserRepository;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserProfileService;
import com.carnerero.agustin.ecommerceapplication.util.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO updateBasicProfile(Long userId, UserRequestDTO request) {
        // Buscar usuario
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID " + userId));

        // Actualizar campos si no son nulos
        if (request.getUserName() != null) {
            user.setUserName(request.getUserName());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getPassword() != null) {
            user.setPassword(request.getPassword()); // opcional: aplicar hash
        }

        // Guardar cambios
        UserEntity updatedUser = userRepository.save(user);

        // Mapear a DTO de respuesta
        return userMapper.toUserResponseDTO(updatedUser);
    }

    @Override
    public UserResponseDTO updateContactInfo(Long userId, String phoneNumber, String address) {
        return null;
    }

    @Override
    public String getUserProfileImage(Long userId) {
        return "";
    }

    @Override
    public String updateProfileImage(Long userId, byte[] imageData, String imageType) {
        return "";
    }

    @Override
    public void deleteProfileImage(Long userId) {

    }

    @Override
    public String getUserBio(Long userId) {
        return "";
    }

    @Override
    public String updateUserBio(Long userId, String bio) {
        return "";
    }
}
