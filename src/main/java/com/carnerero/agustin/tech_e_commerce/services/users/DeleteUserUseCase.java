package com.carnerero.agustin.tech_e_commerce.services.users;

import com.carnerero.agustin.tech_e_commerce.mapper.UserMapper;
import com.carnerero.agustin.tech_e_commerce.repositories.OrderRepository;
import com.carnerero.agustin.tech_e_commerce.repositories.UserRepository;
import com.carnerero.agustin.tech_e_commerce.services.interfaces.ICancelService;
import com.carnerero.agustin.tech_e_commerce.services.interfaces.IDeleteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DeleteUserUseCase implements IDeleteService<Long> {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public void delete(Long id) {
        // Verificar si el usuario existe
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID " + id);
        }

        // Verificar si tiene órdenes
        boolean hasOrders = orderRepository.existsByUserId(id);

        if (hasOrders) {
            // Hibernate borrará con cascade si existen órdenes
            userRepository.deleteById(id);
        } else {
            // Borrar directamente sin cargar colecciones
            userRepository.deleteByIdDirect(id);
        }
    }
}
