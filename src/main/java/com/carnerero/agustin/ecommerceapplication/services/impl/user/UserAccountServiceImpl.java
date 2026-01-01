package com.carnerero.agustin.ecommerceapplication.services.impl.user;

import com.carnerero.agustin.ecommerceapplication.repository.OrderRepository;
import com.carnerero.agustin.ecommerceapplication.repository.UserRepository;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserAccountService;
import com.carnerero.agustin.ecommerceapplication.util.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@AllArgsConstructor

public class UserAccountServiceImpl implements UserAccountService {


    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final UserMapper userMapper;

    @Override
    public boolean activateAccount(Long userId) {
        return false;
    }

    @Override
    public boolean deactivateAccount(Long userId, String reason) {
        return false;
    }

    @Override
    public boolean suspendAccount(Long userId, String reason) {
        return false;
    }

    @Override
    public boolean reactivateAccount(Long userId) {
        return false;
    }

    @Override
    public void deleteAccount(Long userId, String confirmPassword) {
        // Verificar si el usuario existe
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with ID " + userId);
        }

        // Verificar si tiene órdenes
        boolean hasOrders = orderRepository.existsByUserId(userId);

        if (hasOrders) {
            // Hibernate borrará con cascade si existen órdenes
            userRepository.deleteById(userId);
        } else {
            // Borrar directamente sin cargar colecciones
            userRepository.deleteByIdDirect(userId);
        }

    }

    @Override
    public void permanentlyDeleteAccount(Long userId, Long adminId, String reason) {

    }

    @Override
    public boolean isAccountActive(Long userId) {
        return false;
    }

    @Override
    public boolean isAccountVerified(Long userId) {
        return false;
    }
}
