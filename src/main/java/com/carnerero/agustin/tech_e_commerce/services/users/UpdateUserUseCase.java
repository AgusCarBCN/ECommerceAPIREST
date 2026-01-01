package com.carnerero.agustin.tech_e_commerce.services.users;

import com.carnerero.agustin.tech_e_commerce.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.tech_e_commerce.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.tech_e_commerce.entities.UserEntity;
import com.carnerero.agustin.tech_e_commerce.mapper.UserMapper;
import com.carnerero.agustin.tech_e_commerce.repositories.UserRepository;
import com.carnerero.agustin.tech_e_commerce.services.interfaces.IUpdateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@AllArgsConstructor
public class UpdateUserUseCase implements IUpdateService<UserRequestDTO, UserResponseDTO,Long> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public UserResponseDTO update(UserRequestDTO request, Long id) {

        // Buscar usuario
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID " + id));

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

 }
