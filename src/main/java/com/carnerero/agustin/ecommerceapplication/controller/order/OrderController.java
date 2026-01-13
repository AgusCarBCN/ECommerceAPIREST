package com.carnerero.agustin.ecommerceapplication.controller.order;


import com.carnerero.agustin.ecommerceapplication.dtos.requests.ListOfProductsRequestsDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.OrderRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.OrderResponseDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.model.enums.OrderStatus;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
@PreAuthorize("denyAll()")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO,
                                                        Authentication authentication) {

        var response=orderService.createOrder(orderRequestDTO,authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping("/by-user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PageResponse<OrderResponseDTO>> getOrdersByUser(@RequestParam Integer page,
                                                                          Authentication authentication   )
    {
        var response=orderService.getOrdersByUser(page, authentication.getName());
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(response);
    }

    @PatchMapping("/cancel")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@RequestParam Long orderId,
                                                        Authentication authentication
    )  {
        var response=orderService.cancelOrder(orderId, authentication.getName());
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(response);
    }
    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderResponseDTO> updateOrder(@RequestParam Long orderId,
                                                        @RequestParam boolean isAdd,
                                                        @RequestBody ListOfProductsRequestsDTO requestDTO) {
        var response=orderService.modifyOrder(orderId,requestDTO,isAdd);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(response);
    }
}
