package com.carnerero.agustin.ecommerceapplication.services.impl;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.BillResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.BillEntity;
import com.carnerero.agustin.ecommerceapplication.model.entities.OrderEntity;
import com.carnerero.agustin.ecommerceapplication.repository.BillRepository;
import com.carnerero.agustin.ecommerceapplication.repository.OrderRepository;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.BillService;
import com.carnerero.agustin.ecommerceapplication.util.mapper.BillMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final OrderRepository orderRepository;
    private final BillMapper billCustomizedMap;

    @Override
    public BillResponseDTO getBillByOrderId(String email,Long orderId) {

        OrderEntity order = orderRepository.findByIdWithProducts(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        // 1️⃣ Ownership
        if (!order.getUser().getEmail().equals(email)) {
            throw new AccessDeniedException("You cannot cancel this order");
        }

        BillEntity bill = billRepository.findByOrderId(orderId).orElseThrow(() -> new RuntimeException("Bill not found or not created"));
        return billCustomizedMap.toBillResponseDTO(bill);

    }
}
