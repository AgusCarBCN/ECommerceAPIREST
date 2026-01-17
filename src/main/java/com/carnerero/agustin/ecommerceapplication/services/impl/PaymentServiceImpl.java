package com.carnerero.agustin.ecommerceapplication.services.impl;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.PaymentRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PaymentResponseDTO;
import com.carnerero.agustin.ecommerceapplication.exception.BusinessException;
import com.carnerero.agustin.ecommerceapplication.exception.ErrorCode;
import com.carnerero.agustin.ecommerceapplication.model.entities.OrderEntity;
import com.carnerero.agustin.ecommerceapplication.model.enums.PaymentStatus;
import com.carnerero.agustin.ecommerceapplication.repository.BillRepository;
import com.carnerero.agustin.ecommerceapplication.repository.OrderRepository;
import com.carnerero.agustin.ecommerceapplication.repository.PaymentRepository;
import com.carnerero.agustin.ecommerceapplication.repository.ProductCatalogRepository;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.PaymentService;
import com.carnerero.agustin.ecommerceapplication.util.AppConstants;
import com.carnerero.agustin.ecommerceapplication.util.helper.Sort;
import com.carnerero.agustin.ecommerceapplication.util.mapper.PageResponseMapper;
import com.carnerero.agustin.ecommerceapplication.util.mapper.PaymentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.carnerero.agustin.ecommerceapplication.model.entities.PaymentEntity;

import java.math.BigDecimal;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final OrderRepository orderRepository;
    private final ProductCatalogRepository productCatalogRepository;
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
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.ORDER_NOT_FOUND.name(),
                        ErrorCode.ORDER_NOT_FOUND.getDefaultMessage(),
                        HttpStatus.NOT_FOUND));

        if (order.getPayment() != null) {
            throw new BusinessException(
                    ErrorCode.ORDER_PAID.name(),
                    ErrorCode.ORDER_PAID.getDefaultMessage(),
                    HttpStatus.CONFLICT);
        }
        if (!order.isOrderPayable()) {
            throw new BusinessException(
                    ErrorCode.ORDER_NOT_PAYABLE.name(),
                    ErrorCode.ORDER_NOT_PAYABLE.getDefaultMessage(),
                    HttpStatus.CONFLICT);
        }

        PaymentEntity payment = PaymentEntity.builder()
                .paymentMethod(paymentRequest.getPaymentMethod())
                .amount(order.getTotalAmount())
                .order(order)
                .build();

        // Simular pago
        boolean success = paymentSimulationService.simulatePayment(paymentRequest);

        if (success) {
            order.addSuccessfulPayment(payment);
            billRepository.save(order.getBill());
        } else {
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
        final var sorting = Sort.getSort(field, desc, allowedPaymentFields);
        var page = paymentRepository.findPaymentsByUserEmail(email,
                        PageRequest.of(numberOfPages, Sort.PAGE_SIZE, sorting))
                .map(paymentMapper::toPaymentResponseDTO);
        return PageResponseMapper.mapToPageResponse(page);
    }

    @Override
    public String refundPayment(Long paymentId) {
        //Verify if payment exists
        var payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->new BusinessException(
                        ErrorCode.PAYMENT_NOT_FOUND.name(),
                        ErrorCode.PAYMENT_NOT_FOUND.getDefaultMessage(),
                        HttpStatus.NOT_FOUND) );
        if (payment.getPaymentStatus().equals(PaymentStatus.REFUND_PENDING
        )) {
            throw new BusinessException(
                    ErrorCode.PAYMENT_IS_PENDING_REFUND.name(),
                    ErrorCode.PAYMENT_IS_PENDING_REFUND.getDefaultMessage(),
                    HttpStatus.CONFLICT) ;
        }
        if (!payment.getPaymentStatus().equals(PaymentStatus.SUCCESS)) {
            throw  new BusinessException(
                    ErrorCode.PAYMENT_CANNOT_REFUND.getDefaultMessage(),
                    ErrorCode.PAYMENT_CANNOT_REFUND.getDefaultMessage()+payment.getPaymentStatus(),
                    HttpStatus.CONFLICT);
        }
        //Change payment to pending refund
        payment.setPaymentStatus(PaymentStatus.REFUND_PENDING);
        return AppConstants.PENDING_REFUND;
    }

    @Override
    public String confirmRefundPayment(Long paymentId) {
        //Verify if payment exists
        var payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.PAYMENT_NOT_FOUND.name(),
                        ErrorCode.PAYMENT_NOT_FOUND.getDefaultMessage(),
                        HttpStatus.NOT_FOUND));
        if (!payment.getPaymentStatus().equals(PaymentStatus.REFUND_PENDING)){
            throw new BusinessException(
                    ErrorCode.PAYMENT_CANNOT_REFUND.getDefaultMessage(),
                    ErrorCode.PAYMENT_CANNOT_REFUND.getDefaultMessage()+payment.getPaymentStatus(),
                    HttpStatus.CONFLICT);
        }

        var order=orderRepository.findById(payment.getOrder().getId())
                .orElseThrow();
        order.confirmRefund();
        //Get products by order
        var products=order.getProducts();
        //Get bill by order
        var bill=order.getBill();
        //Replace quantity in stock
        products.forEach(product -> {
            //Replace quantity in stock
            //Get product catalog
            var productCatalog=productCatalogRepository.findById(product.getProductCatalog().getId()).orElseThrow();
            productCatalog.restoreStock(product.getQuantity());

        });

        //Change bill
        bill.setTotalAmount(order.getTotalAmount().multiply(BigDecimal.valueOf(-1)));
        bill.setTaxAmount(BigDecimal.ZERO);
        bill.setShippingAmount(BigDecimal.ZERO);
        return AppConstants.CONFIRM_REFUND;
    }
}
