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

        // 3️⃣ List of products to add to order
        List<ProductEntity> products = new ArrayList<>();

        // Init order amount
        BigDecimal total = BigDecimal.ZERO;

        for (ProductRequestDTO p : request.getProducts()) {
            ProductCatalogEntity catalog = productCatalogRepository
                    .findById(p.getProductCatalogId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Product catalog not found: " + p.getProductCatalogId()
                    ));
            // Verificate stock
            int newStock = catalog.getStockQuantity() - p.getQuantity();
            if (newStock < 0) {
                throw new BusinessException("Insufficient stock for product: " + catalog.getProductName());
            }
            //Update stock
            catalog.reduceStock(p.getQuantity());

            //Calculate partial price
            BigDecimal price = catalog.getPrice()
                    .multiply(BigDecimal.valueOf(p.getQuantity().longValue()));

            // Sum to total
            total = total.add(price);
            // Create Product entity
            ProductEntity product = ProductEntity.builder()
                    .quantity(p.getQuantity())
                    .order(order)
                    .productCatalog(catalog)
                    .build();
            //Add to products
            products.add(product);

        }
            // Add products to order
            order.setProducts(products);
            // 4️⃣ Set amount to order
            order.addToTotalAmount(total);
            //order.setTotalAmount(total);

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
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        //Get products
        var products = request.getProducts();
        // 2️⃣ Validar estado de la orden
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new IllegalStateException("Cannot modify order in status: " + order.getStatus());
        }

        BillEntity bill = order.getBill();
        BigDecimal totalAmount = BigDecimal.ZERO; // vamos a recalcular al final

        for (ProductRequestDTO p : products) {

            ProductCatalogEntity catalog = productCatalogRepository
                    .findById(p.getProductCatalogId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            Integer qty = p.getQuantity();

            if (isAdd) {
                // 3️⃣ Validar stock
                if (catalog.getStockQuantity() < qty) {
                    throw new IllegalStateException("Insufficient stock for product: " + catalog.getProductName());
                }

                // 4️⃣ Reducir stock
                catalog.reduceStock(qty);
                //catalog.setStockQuantity(catalog.getStockQuantity() - qty);

                // 5️⃣ Buscar si ya existe en la orden
                ProductEntity product = order.getProducts().stream()
                        .filter(pr -> pr.getProductCatalog().getId().equals(catalog.getId()))
                        .findFirst()
                        .orElse(null);

                if (product == null) {
                    product = ProductEntity.builder()
                            .order(order)
                            .productCatalog(catalog)
                            .quantity(qty)
                            .build();
                    order.addProduct(product);
                } else {
                    product.addQuantity(qty);
                    //product.setQuantity(product.getQuantity()+qty);
                }

            } else {
                // 6️⃣ REMOVE
                ProductEntity product = order.getProducts().stream()
                        .filter(pr -> pr.getProductCatalog().getId().equals(catalog.getId()))
                        .findFirst()
                        .orElseThrow(() ->
                                new IllegalStateException("Product not found in order"));

                if (product.getQuantity().compareTo(qty) < 0) {
                    throw new IllegalStateException("Cannot remove more items than ordered");
                }

                // 7️⃣ Ajustar cantidad o eliminar
                //product.setQuantity(product.getQuantity()-qty);
                product.reduceQuantity(qty);
                if (product.getQuantity() == 0) {
                    order.removeProduct(product); // orphanRemoval hace delete
                }

                // 8️⃣ Restaurar stock
                //catalog.setStockQuantity(catalog.getStockQuantity() + qty);
                catalog.restoreStock(qty);
            }
        }

        // 9️⃣ Recalcular totalAmount desde cero (robusto)
        totalAmount = order.getProducts().stream()
                .map(pr -> pr.getProductCatalog().getPrice()
                        .multiply(new BigDecimal(pr.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        bill.setTotalAmount(totalAmount);
        order.setUpdatedAt(LocalDateTime.now());
        return orderMapper.toOrderResponseDTO(order);
    }

}