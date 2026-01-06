package com.carnerero.agustin.ecommerceapplication.services.impl;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.BillResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.BillEntity;
import com.carnerero.agustin.ecommerceapplication.model.enums.BillStatus;
import com.carnerero.agustin.ecommerceapplication.model.enums.OrderStatus;
import com.carnerero.agustin.ecommerceapplication.model.enums.PaymentStatus;
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

    @Override
    public BillResponseDTO generateBill(Long orderId) {
        var order=orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order not found!"));
        var user=order.getUser();
        var payment=order.getPayment();
        //Payment successful
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        var bill=BillEntity.builder()
                .totalAmount(order.getTotalAmount())
                .status(BillStatus.ACTIVE)
                .build();
        billRepository.save(bill);
        order.setBill(bill);
        //Order paid
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
        var response= billMapper.toBillResponseDTO(bill);
        response.setTaxId(user.getTaxId());
        return response;
    }
}
