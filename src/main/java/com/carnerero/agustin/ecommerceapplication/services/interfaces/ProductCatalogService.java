package com.carnerero.agustin.ecommerceapplication.services.interfaces;

import com.carnerero.agustin.ecommerceapplication.dtos.requests.CreateCategoryRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.ProductCatalogRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.CategoryResponseDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.ProductCatalogResponseDTO;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Service interface for managing products in the catalog.
 * Provides operations for retrieving, filtering, and searching products.
 */
public interface ProductCatalogService {

    /**
     * Retrieves a product by its unique identifier.
     *
     * @param id the product UUID
     * @return an Optional containing the product if found
     */
    ProductCatalogResponseDTO getProductById(UUID id);


    /**
     * Retrieves a paginated list of products that belong to the given category enum.
     *
     * @param categoryId the category id
     * @param field the field used for sorting
     * @param desc sorting order: true for descending, false for ascending
     * @param numberOfPages the page number to retrieve
     * @return a page of products in the specified category
     */
    PageResponse<ProductCatalogResponseDTO> getProductsByCategory(Long categoryId,
                                                                  String field,
                                                                  Boolean desc,
                                                                  Integer numberOfPages);

    /**
     * Retrieves a paginated list of products whose price is between the specified minimum and maximum.
     *
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @param field the field used for sorting
     * @param desc sorting order: true for descending, false for ascending
     * @param numberOfPages the page number to retrieve
     * @return a page of products within the price range
     */
    PageResponse<ProductCatalogResponseDTO> getProductsByPriceRange(BigDecimal minPrice,
                                                                    BigDecimal maxPrice,
                                                                    String field,
                                                                    Boolean desc,
                                                                    Integer numberOfPages);

    /**
     * Retrieves a paginated list of products whose name contains the specified text (case-insensitive).
     *
     * @param name     the text to search for in product names
     * @param field the field used for sorting
     * @param desc sorting order: true for descending, false for ascending
     * @param numberOfPages the page number to retrieve
     * @return a page of products matching the search text
     */
    PageResponse<ProductCatalogResponseDTO> searchProductsByName(String name,
                                                                 String field,
                                                                 Boolean desc,
                                                                 Integer numberOfPages);


        /**
         * Add a new category product in the system.
         *
         * @param  request the new category to add
         * @return  CategoryResponseDTO the category added
         * @throws ProductExistException if the product already exists in the system
         */
        CategoryResponseDTO addCategory(CreateCategoryRequestDTO request);


        /**
         * Add a new catalog product in the system.
         *
         * @param  request the new product to add
         * @return  ProductCatalogResponseDTO
         * @throws ProductExistException if the product already exists in the system
         */

        ProductCatalogResponseDTO addProductCatalog(ProductCatalogRequestDTO request);

        /**
         * Delete a new catalog product in the system.
         *
         * @param productId the id product to delete
         * @return  ProductCatalogResponseDTO
         * @throws ProductExistException if the product already exists in the system
         */

        void deleteProductCatalog(UUID productId);

        /**
         * Update catalog product price.
         *
         * @param request the request to update product
         * @param productId the id product to update
         * @return  ProductCatalogResponseDTO
         * @throws ProductNotFoundException if the product already exists in the system
         */

        public ProductCatalogResponseDTO updateProductCatalog(ProductCatalogRequestDTO request, UUID productId);


    }