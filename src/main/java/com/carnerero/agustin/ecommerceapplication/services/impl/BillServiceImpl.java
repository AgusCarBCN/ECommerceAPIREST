package com.carnerero.agustin.ecommerceapplication.services.impl;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.BillResponseDTO;
import com.carnerero.agustin.ecommerceapplication.repository.BillRepository;
import com.carnerero.agustin.ecommerceapplication.repository.OrderRepository;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.BillService;
import com.carnerero.agustin.ecommerceapplication.util.mapper.BillMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final OrderRepository orderRepository;
    private final BillMapper billMapper;
    @Override
    public BillResponseDTO getBillById(UUID billId) {
        var bill=billRepository.findById(billId).orElseThrow(()->new RuntimeException("Bill not found!"));
        return billMapper.toBillResponseDTO(bill);
    }

    @Override
    public BillResponseDTO getBillByOrderId(Long orderId) {
        var order=orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order not found!"));
        var bill=order.getBill();
        return billMapper.toBillResponseDTO(bill);
    }


}
