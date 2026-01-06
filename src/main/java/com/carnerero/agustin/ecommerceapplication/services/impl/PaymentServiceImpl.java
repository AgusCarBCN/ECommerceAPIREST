package com.carnerero.agustin.ecommerceapplication.services.impl;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.PaymentRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PaymentResponseDTO;
import com.carnerero.agustin.ecommerceapplication.exception.user.BusinessException;
import com.carnerero.agustin.ecommerceapplication.model.entities.BillEntity;
import com.carnerero.agustin.ecommerceapplication.model.entities.OrderEntity;
import com.carnerero.agustin.ecommerceapplication.model.enums.OrderStatus;
import com.carnerero.agustin.ecommerceapplication.model.enums.PaymentStatus;
import com.carnerero.agustin.ecommerceapplication.repository.OrderRepository;
import com.carnerero.agustin.ecommerceapplication.repository.PaymentRepository;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.PaymentService;
import com.carnerero.agustin.ecommerceapplication.util.mapper.PaymentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.carnerero.agustin.ecommerceapplication.model.entities.PaymentEntity;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    @Override
    public PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequest) {
        OrderEntity order = orderRepository.findById(paymentRequest.getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getPayment() != null) {
            throw new BusinessException("Order already paid");
        }

        PaymentEntity payment = PaymentEntity.builder()
                .paymentMethod(paymentRequest.getPaymentMethod())
                .amount(order.getTotalAmount())
                .order(order)
                .build();
        //Order is waiting for payment
        order.setStatus(OrderStatus.PENDING_PAYMENT);
        //Se añade pago a la order
        order.setPayment(payment);
        //Save payment
        paymentRepository.save(payment);

        return paymentMapper.toPaymentResponseDTO(payment);
    }

    @Override
    public PaymentResponseDTO updatePaymentStatus(Long paymentId, PaymentStatus newStatus) {
        var payment=paymentRepository.findById(paymentId).orElseThrow(()->new RuntimeException("Payment not found"));
        payment.setPaymentStatus(newStatus);       //Guardar cambios

        //paymentRepository.save(payment);
        return paymentMapper.toPaymentResponseDTO(payment);
    }

    @Override
    public PaymentResponseDTO getPaymentsByOrder(Long orderId) {
        //Verify if order exists
        var order=orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order not found"));

        var payment=paymentRepository.findByOrderId(orderId);
        return paymentMapper.toPaymentResponseDTO(payment);

    }
}
