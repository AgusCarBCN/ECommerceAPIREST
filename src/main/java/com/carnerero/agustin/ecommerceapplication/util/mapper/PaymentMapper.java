package com.carnerero.agustin.ecommerceapplication.util.mapper;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.PaymentRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PaymentResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.PaymentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = OrderMapper.class)
public interface PaymentMapper {
    PaymentResponseDTO toPaymentResponseDTO(PaymentEntity paymentEntity);
    PaymentEntity toPaymentEntity(PaymentRequestDTO paymentRequestDTO);
}
