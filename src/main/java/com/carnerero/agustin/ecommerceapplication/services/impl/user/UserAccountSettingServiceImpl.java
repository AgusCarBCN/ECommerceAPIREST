package com.carnerero.agustin.ecommerceapplication.services.impl.user;

import com.carnerero.agustin.ecommerceapplication.exception.BusinessException;
import com.carnerero.agustin.ecommerceapplication.exception.ErrorCode;
import com.carnerero.agustin.ecommerceapplication.model.enums.UserStatus;
import com.carnerero.agustin.ecommerceapplication.repository.OrderRepository;
import com.carnerero.agustin.ecommerceapplication.repository.UserRepository;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserAccountSettingService;
import com.carnerero.agustin.ecommerceapplication.util.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor

public class UserAccountSettingServiceImpl implements UserAccountSettingService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final UserMapper userMapper;

    @Override
    public void activateAccount(String email) {
        var user=userRepository.findByEmail(email).orElseThrow(this::userNotFound);
        user.activateUser();
    }
    @Override
    public void deactivateAccount(String email,
                                  String reason) {
        var user=userRepository.findByEmail(email).orElseThrow(this::userNotFound);
        user.deactivateUser(reason);
    }

    @Override
    public void suspendAccount(String email, String reason,Long userId) {
        //Verify if admin exists
        userRepository.findByEmail(email).orElseThrow(this::userNotFound);
        //verify if user exists
        var user = userRepository.findById(userId).orElseThrow(this::userNotFound);
        user.suspendUser();
    }

    @Override
    public void reactivateAccount(String email, Long userId) {
        //Verify if admin exists
        userRepository.findByEmail(email).orElseThrow(()->new BusinessException(
                "ADMIN_NOT_FOUND",
                "Admin not found in they system with email " + email,
                HttpStatus.NOT_FOUND));
        //verify if user exists
        var user = userRepository.findById(userId).orElseThrow(this::userNotFound);
        user.reactivateUser();
    }


    @Override
    public boolean isAccountActive(Long userId) {
        var user=userRepository.findById(userId).orElseThrow(()->new BusinessException(
                "ADMIN_NOT_FOUND",
                "Admin not found in they system with userId " +userId ,
                HttpStatus.NOT_FOUND));
        return user.getStatus().equals(UserStatus.ACTIVE);
    }

    @Override
    public boolean isAccountVerified(Long userId) {
        return false;
    }

    @Override
    public void deleteProfileImage(Long userId) {

    }
    private BusinessException userNotFound() {
        return new BusinessException(
                ErrorCode.USER_NOT_FOUND.name(),
                ErrorCode.USER_NOT_FOUND.getDefaultMessage(),
                HttpStatus.NOT_FOUND);
    }

}
