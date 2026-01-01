package com.carnerero.agustin.ecommerceapplication.services.impl.user;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.exception.user.UserNotFoundException;
import com.carnerero.agustin.ecommerceapplication.model.entities.UserEntity;
import com.carnerero.agustin.ecommerceapplication.model.enums.UserStatus;
import com.carnerero.agustin.ecommerceapplication.repository.OrderRepository;
import com.carnerero.agustin.ecommerceapplication.repository.UserRepository;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserAccountService;
import com.carnerero.agustin.ecommerceapplication.util.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@AllArgsConstructor

public class UserAccountServiceImpl implements UserAccountService {


    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO activateAccount(Long userId) {
        //Search user by id
        var status= UserStatus.ACTIVE;
        var user=userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
        //Activate status
        user.setStatus(status);
        user.setStatusDescription("User active");
        // Guardar cambios
        UserEntity updatedUser = userRepository.save(user);
        return userMapper.toUserResponseDTO(updatedUser);
    }

    @Override
    public UserResponseDTO deactivateAccount(Long userId, String reason) {

        //Search user by id
        var status= UserStatus.INACTIVE;
        var user=userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
        //Activate status
        user.setStatus(status);
        user.setStatusDescription("User inactive by "+reason);
        // Guardar cambios
        UserEntity updatedUser = userRepository.save(user);
        return userMapper.toUserResponseDTO(updatedUser);

    }

    @Override
    public UserResponseDTO suspendAccount(Long userId, String reason) {
        //Search user by id
        var status= UserStatus.SUSPENDED;
        var user=userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
        //Activate status
        user.setStatus(status);
        user.setStatusDescription("User suspended by "+reason);
        // Guardar cambios
        UserEntity updatedUser = userRepository.save(user);
        return userMapper.toUserResponseDTO(updatedUser);
    }

    @Override
    public UserResponseDTO reactivateAccount(Long userId) {
        //Search user by id
        var status= UserStatus.ACTIVE;
        var user=userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
        //Activate status
        user.setStatus(status);
        user.setStatusDescription("User active");
        // Guardar cambios
        UserEntity updatedUser = userRepository.save(user);
        return userMapper.toUserResponseDTO(updatedUser);

    }

    @Override
    public UserResponseDTO deleteAccount(Long userId, String confirmPassword) {
        //Search user by id
        var status= UserStatus.DELETED;
        var user=userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
        //Activate status
        user.setStatus(status);
        user.setStatusDescription("User active");
        // Guardar cambios
        UserEntity updatedUser = userRepository.save(user);
        return userMapper.toUserResponseDTO(updatedUser);

    }

    @Override
    public void permanentlyDeleteAccount(Long userId, Long adminId, String reason) {
        // Verificar si el usuario existe
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with ID " + userId);
        }
        // Verificar si tiene órdenes
        boolean hasOrders = orderRepository.existsByUserId(userId);

        if (hasOrders) {
            // Hibernate borrará con cascade si existen órdenes
            userRepository.deleteById(userId);
        } else {
            // Borrar directamente sin cargar colecciones
            userRepository.deleteByIdDirect(userId);
        }
    }

    @Override
    public boolean isAccountActive(Long userId) {
        var user=userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
        if(user.getStatus().equals(UserStatus.ACTIVE)){
            return true;
        }else return false;
    }

    @Override
    public boolean isAccountVerified(Long userId) {
        return false;
    }
}
