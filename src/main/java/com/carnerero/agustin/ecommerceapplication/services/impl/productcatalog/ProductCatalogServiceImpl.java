package com.carnerero.agustin.ecommerceapplication.services.impl.productcatalog;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.ProductCatalogResponseDTO;
import com.carnerero.agustin.ecommerceapplication.exception.productCatalog.ProductNotFoundException;
import com.carnerero.agustin.ecommerceapplication.repository.CategoryRepository;
import com.carnerero.agustin.ecommerceapplication.repository.ProductCatalogRepository;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.productcatalog.ProductCatalogService;
import com.carnerero.agustin.ecommerceapplication.util.helper.Sort;
import com.carnerero.agustin.ecommerceapplication.util.mapper.PageResponseMapper;
import com.carnerero.agustin.ecommerceapplication.util.mapper.ProductCatalogMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ProductCatalogServiceImpl implements ProductCatalogService {

    private final ProductCatalogRepository productCatalogRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCatalogMapper productCatalogMapper;
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
}
