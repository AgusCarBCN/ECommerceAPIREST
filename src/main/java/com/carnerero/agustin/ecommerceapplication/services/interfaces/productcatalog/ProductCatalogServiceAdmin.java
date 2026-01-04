package com.carnerero.agustin.ecommerceapplication.services.interfaces.productcatalog;


import com.carnerero.agustin.ecommerceapplication.dtos.requests.CategoryRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.CreateCategoryRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.ProductCatalogRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.CategoryResponseDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.ProductCatalogResponseDTO;
import com.carnerero.agustin.ecommerceapplication.exception.productCatalog.ProductExistException;
import com.carnerero.agustin.ecommerceapplication.exception.productCatalog.ProductNotFoundException;

import java.util.UUID;

/**
 * Service interface for managing products in the catalog by admin.
 * Provides operations for create and update products.
 */

public interface ProductCatalogServiceAdmin {

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
