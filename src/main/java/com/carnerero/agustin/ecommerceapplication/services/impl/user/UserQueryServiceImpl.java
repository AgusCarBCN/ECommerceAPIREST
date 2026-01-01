package com.carnerero.agustin.ecommerceapplication.services.impl.user;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.repository.UserRepository;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserQueryService;
import com.carnerero.agustin.ecommerceapplication.util.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@AllArgsConstructor

public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserResponseDTO getUserById(Long userId) {
        var userEntity=userRepository
                .findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        return userMapper.toUserResponseDTO(userEntity);
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        return null;
    }

    @Override
    public UserResponseDTO getUserByUsername(String username) {
        var userEntity=userRepository
                .findByUserNameContainingIgnoreCase(username)
                .orElseThrow(()-> new RuntimeException("User not found"));
        return userMapper.toUserResponseDTO(userEntity);
    }

    @Override
    public PageResponse<UserResponseDTO> getUsersByRole(String roleName, Pageable pageable) {
        return null;
    }

    @Override
    public PageResponse<UserResponseDTO> getActiveUsers(Pageable pageable) {
        return null;
    }

    @Override
    public PageResponse<UserResponseDTO> getUsersByRegistrationDate(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }

    @Override
    public long countAllUsers() {
        return 0;
    }

    @Override
    public long countUsersByStatus(boolean active) {
        return 0;
    }
}
