package com.carnerero.agustin.ecommerceapplication.controller;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.BillResponseDTO;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.BillService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user/bill")
public class BillController {

    private final BillService billService;

    @PostMapping
    public ResponseEntity<BillResponseDTO> createBill(@RequestParam Long orderId) {

        var response=billService.generateBill(orderId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
