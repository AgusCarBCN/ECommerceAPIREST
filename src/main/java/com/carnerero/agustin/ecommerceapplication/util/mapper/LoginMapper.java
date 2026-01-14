package com.carnerero.agustin.ecommerceapplication.util.mapper;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.AuthRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.LoginResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface LoginMapper {

    LoginResponseDTO toUserResponseDTO(UserEntity userEntity);
    UserEntity toUserEntity(AuthRequestDTO userRequestDTO);
}
