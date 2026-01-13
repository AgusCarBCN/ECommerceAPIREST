package com.carnerero.agustin.ecommerceapplication.services.impl;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.PaymentRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
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
import com.carnerero.agustin.ecommerceapplication.util.helper.Sort;
import com.carnerero.agustin.ecommerceapplication.util.mapper.PageResponseMapper;
import com.carnerero.agustin.ecommerceapplication.util.mapper.PaymentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.carnerero.agustin.ecommerceapplication.model.entities.PaymentEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private final static Set<String> allowedPaymentFields = Set.of(
            "amount",
            "createdAt",
            "updatedAt"
    );


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

        if(success){
            order.addSuccessfulPayment(payment);
            billRepository.save(order.getBill());
        }else{
            order.addFailedPayment(payment);
        }

        // Guardar pago en DB
        paymentRepository.save(payment);

        // Retornar DTO
        return paymentMapper.toPaymentResponseDTO(payment);

    }


    @Override
    public PageResponse<PaymentResponseDTO> getUserPayments(String email,
                                                            String field,
                                                            Boolean desc,
                                                            Integer numberOfPages) {
        final var sorting= Sort.getSort(field,desc,allowedPaymentFields);
        var page=paymentRepository.findPaymentsByUserEmail(email,
                        PageRequest.of(numberOfPages, Sort.PAGE_SIZE, sorting))
                .map(paymentMapper::toPaymentResponseDTO);
        return PageResponseMapper.mapToPageResponse(page);
    }
}
