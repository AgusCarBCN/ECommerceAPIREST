package com.carnerero.agustin.ecommerceapplication.util.mapper;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.PaymentRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PaymentResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = OrderMapper.class)
public interface PaymentMapper {
    PaymentResponseDTO toPaymentResponseDTO(PaymentEntity paymentEntity);
    PaymentEntity toPaymentEntity(PaymentRequestDTO paymentRequestDTO);
}
