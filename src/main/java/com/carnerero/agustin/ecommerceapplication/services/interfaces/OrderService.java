package com.carnerero.agustin.ecommerceapplication.services.interfaces;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.ListOfProductsRequestsDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.OrderRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.ProductRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.OrderResponseDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.model.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    /**
     * Create a new order for a user.
     * This includes creating Order, Bill, OrderProducts, and initial Payments.
     * @param orderRequest DTO containing userId, product list and quantities, payment info
     * @return OrdeResponseDTO with full order details
     */
    OrderResponseDTO createOrder(OrderRequestDTO orderRequest,String email);

    /**
     * Retrieve an order by its ID.
     * @param  id of the order
     * @return OrderResponseDTO with details
     */
    OrderResponseDTO getOrderById(Long id);

    /**
     * Retrieve all orders for a specific user.
     * @param email the email of the user
     * @param numberOfPages the page number to retrieve
     *
     * @return PageResponse of OrderResponseDTOs
     */
    PageResponse<OrderResponseDTO> getOrdersByUser(Integer numberOfPages,
                                                   String email);



    /**
     * Cancel an order.
     * This should handle stock restoration and payment reversal if needed.
     * @param orderId the ID of the order
     */
    OrderResponseDTO cancelOrder(Long orderId,String email);

    /**
     * Add a product to an existing order.
     * @param orderId the ID of the order
     * @param requestsDTO the requests of the product to add to order
     */
    OrderResponseDTO modifyOrder(Long orderId, ListOfProductsRequestsDTO requestsDTO, boolean isAdd);




}

