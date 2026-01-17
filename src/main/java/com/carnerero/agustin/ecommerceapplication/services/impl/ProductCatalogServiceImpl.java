package com.carnerero.agustin.ecommerceapplication.services.impl;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.CategoryRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.CreateCategoryRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.ProductCatalogRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.CategoryResponseDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.ProductCatalogResponseDTO;
import com.carnerero.agustin.ecommerceapplication.exception.productCatalog.ProductNotFoundException;
import com.carnerero.agustin.ecommerceapplication.model.entities.CategoryEntity;
import com.carnerero.agustin.ecommerceapplication.model.entities.ProductCatalogEntity;
import com.carnerero.agustin.ecommerceapplication.repository.CategoryRepository;
import com.carnerero.agustin.ecommerceapplication.repository.ProductCatalogRepository;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.ProductCatalogService;
import com.carnerero.agustin.ecommerceapplication.util.helper.Sort;
import com.carnerero.agustin.ecommerceapplication.util.mapper.CategoryMapper;
import com.carnerero.agustin.ecommerceapplication.util.mapper.PageResponseMapper;
import com.carnerero.agustin.ecommerceapplication.util.mapper.ProductCatalogMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional()
@AllArgsConstructor
public class ProductCatalogServiceImpl implements ProductCatalogService {

    private final ProductCatalogRepository productCatalogRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCatalogMapper productCatalogMapper;
    private final CategoryMapper categoryMapper;
    private final static Set<String> allowedProductFields = Set.of(
            "productName",
            "price",
            "createdAt"
    );
    @Override
    public ProductCatalogResponseDTO getProductById(UUID id) {
        var product= productCatalogRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product not found"));
        return productCatalogMapper.toProductCatalogResponseDTO(product);
    }

    @Override
    public PageResponse<ProductCatalogResponseDTO> getProductsByCategory(Long categoryId, String field, Boolean desc, Integer numberOfPages) {
        final var sorting= Sort.getSort(field,desc,allowedProductFields);

        var page=productCatalogRepository.findByCategoryId(categoryId,
                PageRequest.of(numberOfPages, Sort.PAGE_SIZE, sorting))
                .map(productCatalogMapper::toProductCatalogResponseDTO);
        return PageResponseMapper.mapToPageResponse(page);
    }

    @Override
    public PageResponse<ProductCatalogResponseDTO> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, String field, Boolean desc, Integer numberOfPages) {
        final var sorting= Sort.getSort(field,desc,allowedProductFields);

        var page=productCatalogRepository.findByPriceBetween(
                        minPrice,
                        maxPrice,
                        PageRequest.of(numberOfPages, Sort.PAGE_SIZE, sorting))
                .map(productCatalogMapper::toProductCatalogResponseDTO);
        return PageResponseMapper.mapToPageResponse(page);
    }

    @Override
    public PageResponse<ProductCatalogResponseDTO> searchProductsByName(String productName, String field, Boolean desc, Integer numberOfPages) {
        final var sorting= Sort.getSort(field,desc,allowedProductFields);

        var page=productCatalogRepository.findByProductNameContainingIgnoreCase(
                        productName,
                        PageRequest.of(numberOfPages, Sort.PAGE_SIZE, sorting))
                .map(productCatalogMapper::toProductCatalogResponseDTO);
        return PageResponseMapper.mapToPageResponse(page);
    }

    @Override
    public CategoryResponseDTO addCategory(CreateCategoryRequestDTO request) {
        CategoryEntity categoryEntity = categoryMapper.toCreateCategoryEntity(request);
        return categoryMapper.toCategoryDTO(categoryEntity);
    }

    @Override
    @Transactional
    public ProductCatalogResponseDTO addProductCatalog(ProductCatalogRequestDTO request) {
        // 1️⃣ Mapear DTO a Entity
        ProductCatalogEntity product = productCatalogMapper.toProductCatalogEntity(request);

        // 2️⃣ Asignar stockQuantity explícitamente (por seguridad)
        product.setStockQuantity(request.getStockQuantity());

        // 3️⃣ Recuperar categorías existentes de la BD
        Set<Long> categoryIds = request.getCategories()
                .stream()
                .map(CategoryRequestDTO::getId)
                .collect(Collectors.toSet());

        Set<CategoryEntity> categories = new HashSet<>(categoryRepository.findAllById(categoryIds));

        // 4️⃣ Sincronizar ambos lados para Many-to-Many
        for (CategoryEntity category : categories) {
            if (!product.getCategories().contains(category)) {
                product.addCategory(category); // añade al product y category.getProducts()
            }
        }

        var updateAt= LocalDateTime.now();
        product.setUpdatedAt(updateAt);
        // 5️⃣ Guardar el producto (Hibernate manejará la tabla intermedia)
        ProductCatalogEntity saved = productCatalogRepository.save(product);

        // 6️⃣ Convertir a ResponseDTO
        ProductCatalogResponseDTO response = productCatalogMapper.toProductCatalogResponseDTO(saved);

        return response;
    }

    @Override
    public void deleteProductCatalog(UUID productId) {
        //Find product by id
        var product=findProductById(productId);
        //Delete product
        productCatalogRepository.delete(product);
    }

    @Override
    @Transactional
    public ProductCatalogResponseDTO updateProductCatalog(ProductCatalogRequestDTO request, UUID productId) {
        //Find product by id
        var product = findProductById(productId);

        Optional.ofNullable(request.getProductName()).ifPresent(product::setProductName);
        Optional.ofNullable(request.getDescription()).ifPresent(product::setDescription);
        Optional.ofNullable(request.getPrice()).ifPresent(product::setPrice);
        Optional.ofNullable(request.getDiscountPercentage()).ifPresent(product::setDiscountPercentage);
        Optional.ofNullable(request.getStockQuantity()).ifPresent(product::setStockQuantity);
        if (request.getCategories() != null) {
            var categoriesEntity = request.getCategories()
                    .stream()
                    .map(categoryMapper::toEntity)
                    .collect(Collectors.toSet());
            product.setCategories(categoriesEntity);
        }

        var savedProduct = productCatalogRepository.save(product);
        return productCatalogMapper.toProductCatalogResponseDTO(savedProduct);
    }

    private ProductCatalogEntity findProductById(UUID productId) {
        return productCatalogRepository.findById(productId).orElseThrow(()->new EntityNotFoundException("Product not found"));
    }
}
