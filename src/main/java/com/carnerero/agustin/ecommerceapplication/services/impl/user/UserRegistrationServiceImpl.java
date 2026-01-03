package com.carnerero.agustin.ecommerceapplication.services.impl.user;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.exception.user.BusinessException;
import com.carnerero.agustin.ecommerceapplication.model.entities.RoleEntity;
import com.carnerero.agustin.ecommerceapplication.model.entities.UserEntity;
import com.carnerero.agustin.ecommerceapplication.model.enums.Roles;
import com.carnerero.agustin.ecommerceapplication.repository.UserAddressRepository;
import com.carnerero.agustin.ecommerceapplication.repository.UserRepository;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserRegistrationService;
import com.carnerero.agustin.ecommerceapplication.util.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRepository userRepository;
    private final UserAddressRepository userAddressRespository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO registerUser(UserRequestDTO request) {
        return registerUser(request,false);
    }

    @Override
    public UserResponseDTO registerAdminUser(UserRequestDTO request) {
        return registerUser(request,true);
    }

    @Override
    public boolean resendVerificationEmail(String email) {
        return false;
    }

    @Override
    public boolean confirmUserAccount(String verificationToken) {
        return false;
    }

    private UserResponseDTO registerUser(UserRequestDTO request, boolean isAdmin){
        var roles=new HashSet<RoleEntity>();
        // Verificar si el nombre y/o el email están disponibles
        if (!isEmailAvailable(request.getEmail())) {
            throw new BusinessException("Email already exists");
        }
        if (!isUserNameAvailable(request.getUserName())) {
            throw new BusinessException("Username already exists");
        }
        UserEntity userEntity = userMapper.toUserEntity(request);
        var addresses=userEntity.getAddresses();
        // Asignag rol de admin
        if(isAdmin){
            roles.add(RoleEntity.builder()
                    .id(2L)
                    .role(Roles.ADMIN)
                    .build());
        }
        //Asignar rol de usuario
        RoleEntity rol=RoleEntity.builder()
                .id(1L)
                .role(Roles.USER)
                .build();
        roles.add(rol);
        userEntity.setRoles(roles);
        // Lado dueño de la relación: asigna el usuario a cada dirección
        addresses.forEach(userEntity::addAddressSafe);
        UserEntity savedUser = userRepository.save(userEntity);
        return userMapper.toUserResponseDTO(savedUser);
    }

    private boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmailIgnoreCase(email);
    }

    private boolean isUserNameAvailable(String username) {
        return !userRepository.existsByUserNameIgnoreCase(username);
    }


}
