package com.carnerero.agustin.ecommerceapplication.services.impl.user;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.exception.BusinessException;
import com.carnerero.agustin.ecommerceapplication.exception.ErrorCode;
import com.carnerero.agustin.ecommerceapplication.model.enums.Roles;
import com.carnerero.agustin.ecommerceapplication.model.enums.UserStatus;
import com.carnerero.agustin.ecommerceapplication.repository.UserRepository;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserQueryService;
import com.carnerero.agustin.ecommerceapplication.util.helper.Sort;
import com.carnerero.agustin.ecommerceapplication.util.mapper.PageResponseMapper;
import com.carnerero.agustin.ecommerceapplication.util.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor

public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final static Set<String> allowedUserFields = Set.of(
            "name",
            "surname",
            "email",
            "createdAt",
            "updatedAt",
            "status"
    );

    @Override
    public UserResponseDTO getUserById(Long userId) {
        var userEntity=userRepository
                .findById(userId)
                .orElseThrow(this::userNotFound);
        return userMapper.toUserResponseDTO(userEntity);
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        var userEntity=userRepository
                .findByEmail(email)
                .orElseThrow(this::userNotFound);
        return userMapper.toUserResponseDTO(userEntity);
    }

    @Override
    public UserResponseDTO getUserByUsername(String username) {
        var userEntity=userRepository
                .findByNameIgnoreCase(username)
                .orElseThrow(this::userNotFound);
        return userMapper.toUserResponseDTO(userEntity);
    }

    @Override
    public PageResponse<UserResponseDTO> getUsersByRole(String field, Boolean desc, Integer numberOfPages, Roles role) {
        final var sorting= Sort.getSort(field,desc,allowedUserFields);
        var page=userRepository.findAllByRoles_Role(role,
                        PageRequest.of(numberOfPages, Sort.PAGE_SIZE, sorting))
                .map(userMapper::toUserResponseDTO);
        return PageResponseMapper.mapToPageResponse(page);
    }

    @Override
    public PageResponse<UserResponseDTO> getUsersByStatus(String field, Boolean desc, Integer numberOfPages, UserStatus status) {
        final var sorting= Sort.getSort(field,desc,allowedUserFields);
        var page=userRepository.findAllByStatus(status,
                        PageRequest.of(numberOfPages, Sort.PAGE_SIZE, sorting))
                .map(userMapper::toUserResponseDTO);
        return PageResponseMapper.mapToPageResponse(page);
    }

    @Override
    public PageResponse<UserResponseDTO> getActiveUsers(String field, Boolean desc, Integer numberOfPages) {
        final var sorting= Sort.getSort(field,desc,allowedUserFields);
        var page=userRepository.findAllActive(
                PageRequest.of(numberOfPages, Sort.PAGE_SIZE, sorting))
                .map(userMapper::toUserResponseDTO);
        return PageResponseMapper.mapToPageResponse(page);
    }

    @Override
    public PageResponse<UserResponseDTO> getUsersCreatedAfter(LocalDate date, String field, Boolean desc, Integer numberOfPages) {
        final var sorting= Sort.getSort(field,desc,allowedUserFields);
        var page=userRepository.findAllByCreatedAtAfter(
                        PageRequest.of(numberOfPages, Sort.PAGE_SIZE, sorting),date)
                .map(userMapper::toUserResponseDTO);
        return PageResponseMapper.mapToPageResponse(page);
    }

    @Override
    public PageResponse<UserResponseDTO> getUsersCreatedBefore(LocalDate date, String field, Boolean desc, Integer numberOfPages) {
        final var sorting= Sort.getSort(field,desc,allowedUserFields);
        var page=userRepository.findAllByCreatedAtBefore(date,
                        PageRequest.of(numberOfPages, Sort.PAGE_SIZE, sorting))
                .map(userMapper::toUserResponseDTO);
        return PageResponseMapper.mapToPageResponse(page);
    }

    @Override
    public PageResponse<UserResponseDTO> getUsersCreatedEquals(LocalDate date, String field, Boolean desc, Integer numberOfPages) {
        final var sorting= Sort.getSort(field,desc,allowedUserFields);

        var page=userRepository.findByCreatedAt(date,
                        PageRequest.of(numberOfPages, Sort.PAGE_SIZE, sorting))
                .map(userMapper::toUserResponseDTO);
        return PageResponseMapper.mapToPageResponse(page);
    }

    @Override
    public PageResponse<UserResponseDTO> getUsersCreatedBetween(LocalDate startDate, LocalDate endDate, String field, Boolean desc, Integer numberOfPages) {
        final var sorting= Sort.getSort(field,desc,allowedUserFields);
        var page=userRepository.findAllByCreatedAtBetween(
                        startDate,
                        endDate,
                        PageRequest.of(numberOfPages, Sort.PAGE_SIZE, sorting))
                .map(userMapper::toUserResponseDTO);
        return PageResponseMapper.mapToPageResponse(page);
    }


    @Override
    public long countAllUsers() {
        return userRepository.count();
    }

    @Override
    public long countUsersByStatus(UserStatus status) {
        return userRepository.countByStatus(status);
    }

    @Override
    public String getUserProfileImage(Long userId) {
        return "";
    }

    private BusinessException userNotFound() {
        return new BusinessException(
                ErrorCode.USER_NOT_FOUND.name(),
                ErrorCode.USER_NOT_FOUND.getDefaultMessage(),
                HttpStatus.NOT_FOUND);
    }
}
