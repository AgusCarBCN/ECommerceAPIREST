package com.carnerero.agustin.tech_e_commerce.services.users;

import com.carnerero.agustin.tech_e_commerce.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.tech_e_commerce.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.tech_e_commerce.entities.RoleEntity;
import com.carnerero.agustin.tech_e_commerce.entities.Roles;
import com.carnerero.agustin.tech_e_commerce.entities.UserEntity;
import com.carnerero.agustin.tech_e_commerce.mapper.UserMapper;
import com.carnerero.agustin.tech_e_commerce.repositories.UserRepository;
import com.carnerero.agustin.tech_e_commerce.services.interfaces.ICreateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class CreateUserUseCase implements ICreateService<UserRequestDTO, UserResponseDTO> {
    private UserRepository userRepository;
    private UserMapper userMapper;

    @Override
    public UserResponseDTO create(UserRequestDTO request) {

            UserEntity userEntity = userMapper.toUserEntity(request);

            // Asignar rol por defecto
            RoleEntity rol=RoleEntity.builder()
                    .id(1L)
                    .role(Roles.USER)
                    .build();
            userEntity.setRoles(Set.of(rol));

            UserEntity savedUser = userRepository.save(userEntity);

            return userMapper.toUserResponseDTO(savedUser);

    }
}
