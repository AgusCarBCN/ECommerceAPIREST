package com.carnerero.agustin.ecommerceapplication.services.impl.user;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.exception.user.UserNotFoundException;
import com.carnerero.agustin.ecommerceapplication.model.entities.UserEntity;
import com.carnerero.agustin.ecommerceapplication.model.enums.UserStatus;
import com.carnerero.agustin.ecommerceapplication.repository.OrderRepository;
import com.carnerero.agustin.ecommerceapplication.repository.UserRepository;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserAccountSettingService;
import com.carnerero.agustin.ecommerceapplication.util.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@AllArgsConstructor

public class UserAccountSettingServiceImpl implements UserAccountSettingService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final UserMapper userMapper;

    @Override
    public void activateAccount(String email) {
        changeUserStatus(email,UserStatus.ACTIVE,"User active.","");
    }
    @Override
    public void deactivateAccount(String email,
                                             String reason) {
        changeUserStatus(email,UserStatus.DEACTIVATED,"User deactivated.",reason);
    }

    @Override
    public void suspendAccount(String email, String reason) {
         changeUserStatus(email, UserStatus.SUSPENDED, "User suspended.", reason);
    }



    @Override
    public boolean isAccountActive(Long userId) {
        var user=userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
        return user.getStatus().equals(UserStatus.ACTIVE);
    }

    @Override
    public boolean isAccountVerified(Long userId) {
        return false;
    }

    @Override
    public void deleteProfileImage(Long userId) {

    }
    private  void changeUserStatus(String email,
                                              UserStatus status,
                                              String description,
                                              String reason){
        //Search user by id
        var updatedAt= LocalDateTime.now();
        var user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));
        var message=description;
        //Activate status
        user.setStatus(status);
        user.setUpdatedAt(updatedAt);
        if(!reason.isBlank()){
            message+=reason;
        }
        user.setStatusDescription(message);

        // Guardar cambios
        UserEntity updatedUser = userRepository.save(user);

    }
}
