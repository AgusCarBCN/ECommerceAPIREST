package com.carnerero.agustin.ecommerceapplication.services.impl;


import com.carnerero.agustin.ecommerceapplication.dtos.requests.PaymentRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
public class PaymentSimulationService {

    public boolean simulatePayment(PaymentRequestDTO paymentDetails) {

        log.info("Simulando pago para la tarjeta : {}", paymentDetails.getCardNumber());
        // Simulación simple:
        // 80% de probabilidad de éxito, 20% de fallo
        return Math.random() > 0.2;
    }
}