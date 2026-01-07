package com.carnerero.agustin.ecommerceapplication.util.mapper;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserAddressRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.RolesDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserAddressResponseDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.RoleEntity;
import com.carnerero.agustin.ecommerceapplication.model.entities.UserAddressEntity;
import com.carnerero.agustin.ecommerceapplication.model.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    //@Mapping(target = "status", source = "status")
    //@Mapping(target = "roles", source = "roles")
    //@Mapping(target = "addresses", source = "addresses")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    //@Mapping(target = "userName", expression = "java(userEntity.getUserName())")
    UserResponseDTO toUserResponseDTO(UserEntity userEntity);
    UserEntity toUserEntity(UserRequestDTO userRequestDTO);
    //Roles mapper
    RolesDTO toRolesDTO(RoleEntity roleEntity);
    RoleEntity toRoleEntity(RolesDTO rolesDTO);
    //Address mapper
    @Mapping(target = "addressType", source = "addressType")
    UserAddressResponseDTO toUserAddressDTO(UserAddressEntity userAddressEntity);
    @Mapping(target = "addressType", source = "addressType")
    UserAddressEntity toUserAddressEntity(UserAddressRequestDTO userAddressRequestDTO);
}
