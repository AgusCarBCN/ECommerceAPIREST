package com.carnerero.agustin.tech_e_commerce.mapper;

import com.carnerero.agustin.tech_e_commerce.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.tech_e_commerce.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.tech_e_commerce.entities.UserEntity;
import org.apache.catalina.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toUserResponseDTO(UserEntity userEntity);
    UserEntity toUserEntity(UserRequestDTO userRequestDTO);
}
