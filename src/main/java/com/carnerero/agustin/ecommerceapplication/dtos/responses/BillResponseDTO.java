package com.carnerero.agustin.ecommerceapplication.dtos.responses;


import com.carnerero.agustin.ecommerceapplication.model.enums.BillStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BillResponseDTO {
        private UUID id;
        private BigDecimal totalAmount;
        private BigDecimal taxAmount;
        private BigDecimal shippingAmount;
        private LocalDateTime issuedAt;
        private BillStatus status;
        private List<ProductResponseDTO> products;

}
