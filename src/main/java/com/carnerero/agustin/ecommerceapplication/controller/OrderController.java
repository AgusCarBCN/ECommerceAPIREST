package com.carnerero.agustin.ecommerceapplication.controller;


import com.carnerero.agustin.ecommerceapplication.dtos.requests.ListOfProductsRequestsDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.OrderRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.OrderResponseDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
@PreAuthorize("hasRole('USER')")
public class OrderController {

    private final OrderService orderService;
    // ---------------------------
    // Create Order
    // ---------------------------
    @Operation(
            summary = "Create a new order",
            description = "Creates a new order for the authenticated user.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or empty product list"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO,
                                                        Authentication authentication) {

        var response=orderService.createOrder(orderRequestDTO,authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // ---------------------------
    // Get Orders by User
    // ---------------------------
    @Operation(
            summary = "Get orders for authenticated user",
            description = "Returns paginated orders for the currently authenticated user.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping("/by-user")
    public ResponseEntity<PageResponse<OrderResponseDTO>> getOrdersByUser(@RequestParam Integer page,
                                                                          Authentication authentication   )
    {
        var response=orderService.getOrdersByUser(page, authentication.getName());
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(response);
    }

    // ---------------------------
    // Cancel Order
    // ---------------------------
    @Operation(
            summary = "Cancel an order",
            description = "Cancels an order for the authenticated user. Only orders in cancelable state can be canceled.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order canceled successfully"),
            @ApiResponse(responseCode = "400", description = "Order cannot be canceled in its current state"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @PatchMapping("/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@RequestParam Long orderId,
                                                        Authentication authentication
    )  {
        var response=orderService.cancelOrder(orderId, authentication.getName());
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(response);
    }


    // ---------------------------
    // Update Order
    // ---------------------------
    @Operation(
            summary = "Update an existing order",
            description = "Updates an order with a new list of products.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or empty product list"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @PutMapping("/update")
    public ResponseEntity<OrderResponseDTO> updateOrder(@RequestParam Long orderId,
                                                        @RequestBody ListOfProductsRequestsDTO requestDTO) {
        var response=orderService.modifyOrder(orderId,requestDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(response);
    }
}
