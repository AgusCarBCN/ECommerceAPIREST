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
        var user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));
        user.activateUser();
    }
    @Override
    public void deactivateAccount(String email,
                                  String reason) {
        var user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));
        user.deactivateUser(reason);
    }

    @Override
    public void suspendAccount(String email, String reason,Long userId) {
        //Verify if admin exists
        userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("Admin not found"));
        //verify if user exists
        var user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
        user.suspendUser();
    }

    @Override
    public void reactivateAccount(String email, Long userId) {
        //Verify if admin exists
        userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("Admin not found"));
        //verify if user exists
        var user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
        user.reactivateUser();
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

}
