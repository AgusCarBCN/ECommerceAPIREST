package com.carnerero.agustin.ecommerceapplication.services.impl;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.PaymentRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PaymentResponseDTO;
import com.carnerero.agustin.ecommerceapplication.exception.user.BusinessException;
import com.carnerero.agustin.ecommerceapplication.model.entities.BillEntity;
import com.carnerero.agustin.ecommerceapplication.model.entities.OrderEntity;
import com.carnerero.agustin.ecommerceapplication.model.enums.BillStatus;
import com.carnerero.agustin.ecommerceapplication.model.enums.OrderStatus;
import com.carnerero.agustin.ecommerceapplication.model.enums.PaymentStatus;
import com.carnerero.agustin.ecommerceapplication.repository.BillRepository;
import com.carnerero.agustin.ecommerceapplication.repository.OrderRepository;
import com.carnerero.agustin.ecommerceapplication.repository.PaymentRepository;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.PaymentService;
import com.carnerero.agustin.ecommerceapplication.util.mapper.PaymentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.carnerero.agustin.ecommerceapplication.model.entities.PaymentEntity;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final OrderRepository orderRepository;
    private final BillRepository billRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentSimulationService paymentSimulationService;
    @Override
    public PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequest) {

        OrderEntity order = orderRepository.findById(paymentRequest.getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getPayment() != null) {
            throw new BusinessException("Order already paid");
        }
        if(!order.isOrderPayable()){
            throw new BusinessException("Order is not payable");
        }

        PaymentEntity payment = PaymentEntity.builder()
                .paymentMethod(paymentRequest.getPaymentMethod())
                .amount(order.getTotalAmount())
                .order(order)
                .build();

        // Simular pago
        boolean success = paymentSimulationService.simulatePayment(paymentRequest);

        // Actualizar estado del pago y de la orden según resultado
        if (success) {
            payment.completePayment();
            order.setStatus(OrderStatus.PAID);
            //Crear factura
            BillEntity bill=BillEntity.builder()
                    .status(BillStatus.ACTIVE)
                    .totalAmount(order.getTotalAmount())
                    .shippingAmount(order.getShippingAmount())
                    .taxAmount(order.getTaxAmount())
                    .order(order)
                    .build();
            billRepository.save(bill);
            order.setBill(bill);
        } else {
            payment.failPayment();
            order.setStatus(OrderStatus.PENDING_PAYMENT);
        }

        // Asignar pago a la orden
        order.setPayment(payment);

        // Guardar pago en DB
        paymentRepository.save(payment);

        // Retornar DTO
        return paymentMapper.toPaymentResponseDTO(payment);

    }


    @Override
    public List<PaymentResponseDTO> getUserPayments(String email) {

        var payments= paymentRepository.findPaymentsByUserEmail(email);
        return payments.stream().map(paymentMapper::toPaymentResponseDTO).toList();


    }
}
