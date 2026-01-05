package com.carnerero.agustin.ecommerceapplication.services.impl;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.ListOfProductsRequestsDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.OrderRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.ProductRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.OrderResponseDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.ProductCatalogResponseDTO;
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
import com.carnerero.agustin.ecommerceapplication.util.mapper.ProductCatalogMapper;
import com.carnerero.agustin.ecommerceapplication.util.mapper.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        var loginRequest = request.getUser();
        UserEntity user = userRepository.findByEmail(
                loginRequest.getEmail()).orElseThrow(() -> new BusinessException("User not found"));

        // Verify password
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new BusinessException("Wrong Password");
        }

        // 1️⃣ Create OrderEntity
        OrderEntity order = orderMapper.toOrderEntity(request);

        // 2️⃣ Create BillEntity

        BillEntity bill = BillEntity.builder()
                .taxId(request.getTaxId())
                .totalAmount(BigDecimal.ZERO)
                .build();

        //Add bill and user to order
        order.setBill(bill);
        order.setUser(user);

        //Set Status to order as CREATED
        order.setStatus(OrderStatus.CREATED);

        // 3️⃣ Validate products
        if (request.getProducts() == null || request.getProducts().isEmpty()) {
            throw new BusinessException("Order must contain at least one product");
        }

        // 3️⃣ List of products to add to order
        List<ProductEntity> products = new ArrayList<>();

        // Init bill's amount
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
            catalog.setStockQuantity(newStock);

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
            // 4️⃣ Set amount to bill
            bill.setTotalAmount(total);
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
    public PageResponse<OrderResponseDTO> getOrdersByUser(Integer numberOfPages, Long userId) {

        var page = orderRepository.findByUserId(userId, PageRequest.of(numberOfPages, Sort.PAGE_SIZE))
                .map(orderMapper::toOrderResponseDTO);

        return PageResponseMapper.mapToPageResponse(page);
    }

    // Only admin
    @Override
    public OrderResponseDTO changeOrderStatus(Long orderId, OrderStatus newStatus) {
        var order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setStatus(newStatus);
        return orderMapper.toOrderResponseDTO(order);
    }


    @Override
    public OrderResponseDTO cancelOrder(Long orderId) {
        var order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setStatus(OrderStatus.CANCELLED);
        return orderMapper.toOrderResponseDTO(order);
    }

    @Transactional
    @Override
    public void deleteOrder(Long orderId) {
        var order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        orderRepository.delete(order);
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
            BigDecimal priceChange = catalog.getPrice().multiply(BigDecimal.valueOf(qty));

            if (isAdd) {
                // 3️⃣ Validar stock
                if (catalog.getStockQuantity() < qty) {
                    throw new IllegalStateException("Insufficient stock for product: " + catalog.getProductName());
                }

                // 4️⃣ Reducir stock
                catalog.setStockQuantity(catalog.getStockQuantity() - qty);

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
                    product.setQuantity(product.getQuantity()+qty);
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
                product.setQuantity(product.getQuantity()-qty);
                if (product.getQuantity() == 0) {
                    order.removeProduct(product); // orphanRemoval hace delete
                }

                // 8️⃣ Restaurar stock
                catalog.setStockQuantity(catalog.getStockQuantity() + qty);
            }
        }

        // 9️⃣ Recalcular totalAmount desde cero (robusto)
        totalAmount = order.getProducts().stream()
                .map(pr -> pr.getProductCatalog().getPrice()
                        .multiply(new BigDecimal(pr.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        bill.setTotalAmount(totalAmount);
        return orderMapper.toOrderResponseDTO(order);
    }

}