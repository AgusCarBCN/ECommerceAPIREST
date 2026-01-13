package com.carnerero.agustin.ecommerceapplication.services.impl.user;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.UpdateUserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserAddressRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.exception.address.AddressNotFoundException;
import com.carnerero.agustin.ecommerceapplication.exception.user.UserNotFoundException;
import com.carnerero.agustin.ecommerceapplication.model.entities.UserAddressEntity;
import com.carnerero.agustin.ecommerceapplication.model.entities.UserEntity;
import com.carnerero.agustin.ecommerceapplication.repository.UserAddressRepository;
import com.carnerero.agustin.ecommerceapplication.repository.UserRepository;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserUpdateService;
import com.carnerero.agustin.ecommerceapplication.util.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional
@AllArgsConstructor
public class UserUpdateServiceImpl implements UserUpdateService {

    private final UserRepository userRepository;
    private final UserAddressRepository userAddressRespository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO updateUserAddress(String email,
                                             Long userAddressId,
                                             UserAddressRequestDTO request) {
        // 1️⃣ Buscar usuario
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with ID " + email));
        var addresses = user.getAddresses();
        // 2️⃣ Buscar dirección
        UserAddressEntity address = userAddressRespository.findById(userAddressId)
                .orElseThrow(() ->
                        new AddressNotFoundException("Address not found with ID " + userAddressId));

        // 3️⃣ Verificar que la dirección pertenece al usuario
        if (!address.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Address does not belong to the user");
        }
        // Si se marca como default, limpiar otras direcciones
        if (request.getIsDefault() == true) {
            addresses.forEach(userAddress -> userAddress.setIsDefault(false));
        }

        // 5️⃣ Actualizar campos (NO crear entidad nueva)
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());
        address.setAddressType(request.getAddressType());
        address.setIsDefault(request.getIsDefault());
        address.setUpdatedAt(LocalDateTime.now());

        // ️⃣ Guardar cambios en direccion
        userAddressRespository.save(address);

        return userMapper.toUserResponseDTO(user);
    }

    @Override
    public String updateProfileImage(Long userId, byte[] imageData, String imageType) {
        return "";
    }


    @Override

    public UserResponseDTO updateUserFields(String email, UpdateUserRequestDTO request) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID " + email));

        if (request.getName() != null) user.setName(request.getName());
        if (request.getSurname() != null) user.setSurname(request.getSurname());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPassword() != null) user.setPassword(request.getPassword());
      //  if (request.getProfileImage() != null) user.se(request.getProfileImage());

        user.setUpdatedAt(LocalDateTime.now());
        return userMapper.toUserResponseDTO(user);
    }
}
