package com.carnerero.agustin.ecommerceapplication.services.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    public OrderResponseDTO createOrder(OrderRequestDTO request) {

        // Find user by username or email
        var loginRequest=request.getUser();
        UserEntity user = userRepository.findByEmail(
                loginRequest.getEmail()).orElseThrow(() -> new BusinessException("User not found"));

        // Verify password
        if(!user.getPassword().equals(loginRequest.getPassword())) {
            throw  new BusinessException("Wrong Password");
        }

        // 1️⃣ Crear Order
        OrderEntity order = orderMapper.toOrderEntity(request);

        // 2️⃣ Crear Bill
        BillEntity bill = BillEntity.builder()
                .taxId(request.getTaxId())
                .totalAmount(BigDecimal.ZERO)
                .build();

        order.setBill(bill);
        order.setUser(user);
        order.setStatus(OrderStatus.CREATED);
        // 3️⃣ Productos
        List<ProductEntity> products = new ArrayList<>();

        BigDecimal total = BigDecimal.ZERO;

        for (ProductRequestDTO p : request.getProducts()) {
            ProductCatalogEntity catalog = productCatalogRepository
                    .findById(p.getProductCatalogId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Product catalog not found: " + p.getProductCatalogId()
                    ));

            ProductEntity product = ProductEntity.builder()
                    .quantity(p.getQuantity())
                    .order(order)
                    .productCatalog(catalog)
                    .build();


            products.add(product);

            BigDecimal price = catalog.getPrice()
                    .multiply(BigDecimal.valueOf(p.getQuantity().longValue()));

            total = total.add(price);
        }
        if (products.isEmpty()) {
            throw new EntityNotFoundException("Products not found. Orders must contain products ");
        } else {
            order.setProducts(products);

            // 4️⃣ Total bill
            bill.setTotalAmount(total);

            // 5️⃣ Guardar (cascade hace el resto)
            OrderEntity savedOrder = orderRepository.save(order);

            // 6️⃣ Devolver DTO
            return orderMapper.toOrderResponseDTO(savedOrder);

        }
    }


    @Override
    public OrderResponseDTO getOrderById(Long orderId) {
        var order=orderRepository.findById(orderId).orElseThrow(()->new EntityNotFoundException("Order not found"));
        return orderMapper.toOrderResponseDTO(order);
    }

    @Override
    public PageResponse<OrderResponseDTO> getOrdersByUser(Integer numberOfPages, Long userId) {

        var page=orderRepository.findByUserId(userId,PageRequest.of(numberOfPages,Sort.PAGE_SIZE))
                .map(orderMapper::toOrderResponseDTO);

        return PageResponseMapper.mapToPageResponse(page);
    }

    // Only admin
    @Override
    public OrderResponseDTO changeOrderStatus(Long orderId, OrderStatus newStatus) {
        var order=orderRepository.findById(orderId).orElseThrow(()->new EntityNotFoundException("Order not found"));
        order.setStatus(newStatus);
        return orderMapper.toOrderResponseDTO(order);
    }


    @Override
    public OrderResponseDTO cancelOrder(Long orderId) {
        var order=orderRepository.findById(orderId).orElseThrow(()->new EntityNotFoundException("Order not found"));
        order.setStatus(OrderStatus.CANCELLED);
        return orderMapper.toOrderResponseDTO(order);
    }
    @Transactional
    @Override
    public void deleteOrder(Long orderId) {
        var order=orderRepository.findById(orderId).orElseThrow(()->new EntityNotFoundException("Order not found"));
        orderRepository.delete(order);
    }
}
