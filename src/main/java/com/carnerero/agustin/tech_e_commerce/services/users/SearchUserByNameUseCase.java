package com.carnerero.agustin.tech_e_commerce.services.users;


import com.carnerero.agustin.tech_e_commerce.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.tech_e_commerce.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.tech_e_commerce.mapper.UserMapper;
import com.carnerero.agustin.tech_e_commerce.repositories.UserRepository;
import com.carnerero.agustin.tech_e_commerce.services.interfaces.IReadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class SearchUserByNameUseCase implements IReadService<UserResponseDTO,String> {

    private UserRepository userRepository;
    private UserMapper userMapper;


    @Override
    public UserResponseDTO getOne(String name) {
        var userEntity=userRepository
                .findByUserNameContainingIgnoreCase(name)
                .orElseThrow(()-> new RuntimeException("User not found"));
        return userMapper.toUserResponseDTO(userEntity);
    }
}
