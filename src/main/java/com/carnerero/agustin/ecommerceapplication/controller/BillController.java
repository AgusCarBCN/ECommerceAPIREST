package com.carnerero.agustin.ecommerceapplication.controller;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.BillResponseDTO;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.BillService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user/bill")
public class BillController {

    private final BillService billService;

    @GetMapping
    public ResponseEntity<BillResponseDTO> getBillByOrder(@RequestParam Long orderId,
                                                          Authentication authentication) {

        var response= billService.getBillByOrderId(authentication.getName(),orderId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
