package com.carnerero.agustin.ecommerceapplication.util.mapper;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.RolesDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.RoleEntity;
import com.carnerero.agustin.ecommerceapplication.model.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "status", source = "status")
    UserResponseDTO toUserResponseDTO(UserEntity userEntity);
    UserEntity toUserEntity(UserRequestDTO userRequestDTO);
    RolesDTO toRolesDTO(RoleEntity roleEntity);
    RoleEntity toRoleEntity(RolesDTO rolesDTO);
}
