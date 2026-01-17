package com.carnerero.agustin.ecommerceapplication.controller;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.BillResponseDTO;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.BillService;
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
@RequestMapping("/bill")
@PreAuthorize("hasRole('USER')")
public class BillController {

    private final BillService billService;
    // ---------------------------
    // Get Bill by Order ID
    // ---------------------------
    @Operation(
            summary = "Get bill by order ID",
            description = "Retrieves the bill associated with a specific order for the authenticated user.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or order ID"),
            @ApiResponse(responseCode = "404", description = "Bill or order not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping
    public ResponseEntity<BillResponseDTO> getBillByOrder(@RequestParam Long orderId,
                                                          Authentication authentication) {

        var response= billService.getBillByOrderId(authentication.getName(),orderId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
