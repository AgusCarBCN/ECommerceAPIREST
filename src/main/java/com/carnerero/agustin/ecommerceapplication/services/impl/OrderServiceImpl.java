package com.carnerero.agustin.ecommerceapplication.services.impl;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.ListOfProductsRequestsDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.OrderRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.ProductRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.OrderResponseDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.exception.user.BusinessException;
import com.carnerero.agustin.ecommerceapplication.model.entities.*;
import com.carnerero.agustin.ecommerceapplication.model.enums.OrderStatus;
import com.carnerero.agustin.ecommerceapplication.repository.OrderRepository;
import com.carnerero.agustin.ecommerceapplication.repository.ProductCatalogRepository;
import com.carnerero.agustin.ecommerceapplication.repository.UserRepository;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.OrderService;
import com.carnerero.agustin.ecommerceapplication.util.helper.Sort;
import com.carnerero.agustin.ecommerceapplication.util.mapper.OrderMapper;
import com.carnerero.agustin.ecommerceapplication.util.mapper.PageResponseMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductCatalogRepository productCatalogRepository;
    private final OrderMapper orderMapper;


    @Transactional
    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO request, String email) {

        UserEntity user = userRepository.findByEmail(
                email).orElseThrow(() -> new BusinessException("User not found"));

        // 1️⃣ Create OrderEntity
        OrderEntity order = orderMapper.toOrderEntity(request);

        // 2️⃣ Set user
        order.setUser(user);

        // 3️⃣ Validate products
        if (request.getProducts() == null || request.getProducts().isEmpty()) {
            throw new BusinessException("Order must contain at least one product");
        }
        // Init order amount
        BigDecimal total;
        for (ProductRequestDTO p : request.getProducts()) {
            ProductCatalogEntity catalog = getProductCatalogById(p);
            order.addProduct(catalog, p.getQuantity());
        }
            // 5️⃣ Save
            OrderEntity savedOrder = orderRepository.save(order);

            // 6️⃣ Return DTO
            return orderMapper.toOrderResponseDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO getOrderById(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

       return orderMapper.toOrderResponseDTO(order);

    }

    @Override
    public PageResponse<OrderResponseDTO> getOrdersByUser(Integer numberOfPages, String email) {

        var page = orderRepository.findByUserEmail(email, PageRequest.of(numberOfPages, Sort.PAGE_SIZE))
                .map(orderMapper::toOrderResponseDTO);

        return PageResponseMapper.mapToPageResponse(page);
    }

    @Override
    @Transactional
    public OrderResponseDTO cancelOrder(Long orderId, String email) {

        OrderEntity order = orderRepository.findByIdWithProducts(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // 1️⃣ Ownership
        if (!order.getUser().getEmail().equals(email)) {
            throw new AccessDeniedException("You cannot cancel this order");
        }

        // 2️⃣ Validación de estado
        if (!order.isCancelableByClient()) {
            throw new IllegalStateException("Order cannot be canceled");
        }

        // 3️⃣ Restaurar stock
        for (ProductEntity product : order.getProducts()) {
            var productCatalog=product.getProductCatalog();
                productCatalog.restoreStock(product.getQuantity());
        }

        // 4️⃣ Cambiar estado
        order.cancelByClient();

        return orderMapper.toOrderResponseDTO(order);
    }


    @Transactional
    @Override
    public OrderResponseDTO modifyOrder(
            Long orderId,
            ListOfProductsRequestsDTO request,
            boolean isAdd
    ) {
        // 1️⃣ Cargar la orden
        OrderEntity order = orderRepository.findByIdWithProducts(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // 2️⃣ Validar estado de la orden
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new IllegalStateException("Cannot modify order in status: " + order.getStatus());
        }

        // 1️⃣ Primero quitar productos
        if (request.getProductsToRemove() != null) {
            for (ProductRequestDTO p : request.getProductsToRemove()) {
                ProductCatalogEntity catalog = getProductCatalogById(p);
                order.removeProduct(catalog, p.getQuantity());
            }
        }

        // 2️⃣ Luego añadir productos
        if (request.getProductsToAdd() != null) {
            for (ProductRequestDTO p : request.getProductsToAdd()) {
                ProductCatalogEntity catalog =getProductCatalogById(p);
                order.addProduct(catalog, p.getQuantity());
            }
        }

        return orderMapper.toOrderResponseDTO(order);
    }
    private ProductCatalogEntity getProductCatalogById(ProductRequestDTO p) {
        return productCatalogRepository
                .findById(p.getProductCatalogId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Product catalog not found: " + p.getProductCatalogId()
                ));
    }
}