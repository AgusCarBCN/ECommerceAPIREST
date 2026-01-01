package com.carnerero.agustin.ecommerceapplication.services.impl.user;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.RoleEntity;
import com.carnerero.agustin.ecommerceapplication.model.entities.UserAddressEntity;
import com.carnerero.agustin.ecommerceapplication.model.entities.UserEntity;
import com.carnerero.agustin.ecommerceapplication.model.enums.Roles;
import com.carnerero.agustin.ecommerceapplication.model.enums.UserStatus;
import com.carnerero.agustin.ecommerceapplication.repository.UserAddressRespository;
import com.carnerero.agustin.ecommerceapplication.repository.UserRepository;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserRegistrationService;
import com.carnerero.agustin.ecommerceapplication.util.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRepository userRepository;
    private final UserAddressRespository userAddressRespository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO registerUser(UserRequestDTO request) {
        UserEntity userEntity = userMapper.toUserEntity(request);
        var addresses=userEntity.getAddresses();
        // Asignar rol por defecto
        RoleEntity rol=RoleEntity.builder()
                .id(1L)
                .role(Roles.USER)
                .build();
        userEntity.setRoles(Set.of(rol));
        // Lado dueño de la relación: asigna el usuario a cada dirección
        addresses.forEach(address -> userEntity.addAddressSafe(address));


        UserEntity savedUser = userRepository.save(userEntity);

        return userMapper.toUserResponseDTO(savedUser);
    }

    @Override
    public boolean isEmailAvailable(String email) {
        return false;
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        return false;
    }

    @Override
    public void validateRegistrationData(UserRequestDTO request) {

    }

    @Override
    public boolean resendVerificationEmail(String email) {
        return false;
    }

    @Override
    public boolean confirmUserAccount(String verificationToken) {
        return false;
    }
}
