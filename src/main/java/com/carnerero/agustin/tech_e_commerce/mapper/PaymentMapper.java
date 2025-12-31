package com.carnerero.agustin.tech_e_commerce.mapper;

import com.carnerero.agustin.tech_e_commerce.dtos.requests.PaymentRequestDTO;
import com.carnerero.agustin.tech_e_commerce.dtos.responses.PaymentResponseDTO;
import com.carnerero.agustin.tech_e_commerce.entities.PaymentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = OrderMapper.class)
public interface PaymentMapper {
    PaymentResponseDTO toPaymentResponseDTO(PaymentEntity paymentEntity);
    PaymentEntity toPaymentEntity(PaymentRequestDTO paymentRequestDTO);
}
