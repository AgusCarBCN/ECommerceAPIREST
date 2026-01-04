package com.carnerero.agustin.ecommerceapplication.services.interfaces;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.OrderRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.OrderResponseDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.model.enums.OrderStatus;

public interface OrderService {

    /**
     * Create a new order for a user.
     * This includes creating Order, Bill, OrderProducts, and initial Payments.
     * @param orderRequest DTO containing userId, product list and quantities, payment info
     * @return OrdeResponseDTO with full order details
     */
    OrderResponseDTO createOrder(OrderRequestDTO orderRequest);

    /**
     * Retrieve an order by its ID.
     * @param orderId the ID of the order
     * @return OrderResponseDTO with details
     */
    OrderResponseDTO getOrderById(Long orderId);

    /**
     * Retrieve all orders for a specific user.
     * @param userId the ID of the user
     * @return PageResponse of OrderResponseDTOs
     */
    PageResponse<OrderResponseDTO> getOrdersByUser(Long userId);

    /**
     * Update the status of an order.
     * @param orderId the ID of the order
     * @param newStatus new status to set (enum OrderStatus)
     * @return updated OrderResponseDTO
     */
    OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus newStatus);

    /**
     * Cancel an order.
     * This should handle stock restoration and payment reversal if needed.
     * @param orderId the ID of the order
     */
    void cancelOrder(Long orderId);
}

