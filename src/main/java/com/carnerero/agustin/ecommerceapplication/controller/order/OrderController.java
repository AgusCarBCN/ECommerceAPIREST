package com.carnerero.agustin.ecommerceapplication.controller.order;


import com.carnerero.agustin.ecommerceapplication.dtos.requests.ListOfProductsRequestsDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.OrderRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.OrderResponseDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.model.enums.OrderStatus;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.OrderService;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.productcatalog.ProductCatalogServiceAdmin;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {

        var response=orderService.createOrder(orderRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping("/by-user")
    public ResponseEntity<PageResponse<OrderResponseDTO>> getOrdersByUser(@RequestParam Integer page,
                                                                          @RequestParam Long userId)
    {
        var response=orderService.getOrdersByUser(page, userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(response);
    }
    @PatchMapping("/change-status")
    public ResponseEntity<OrderResponseDTO> changeOrderStatus(@RequestParam Long orderId, @RequestParam OrderStatus newStatus) {
        var response=orderService.changeOrderStatus(orderId, newStatus);
        return  ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(response);
    }
    @PatchMapping("/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@RequestParam Long orderId) {
        var response=orderService.cancelOrder(orderId);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(response);
    }
    @PutMapping("/update")
    public ResponseEntity<OrderResponseDTO> updateOrder(@RequestParam Long orderId,
                                                        @RequestParam boolean isAdd,
                                                        @RequestBody ListOfProductsRequestsDTO requestDTO) {
        var response=orderService.modifyOrder(orderId,requestDTO,isAdd);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(response);
    }
}
