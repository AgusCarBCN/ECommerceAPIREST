package com.carnerero.agustin.ecommerceapplication.controller.productcatalog;


import com.carnerero.agustin.ecommerceapplication.dtos.requests.ProductCatalogRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.ProductCatalogResponseDTO;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.productcatalog.ProductCatalogServiceAdmin;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/products/admin")
public class ProductCatalogAdminController {

    private final ProductCatalogServiceAdmin productCatalogServiceAdmin;

    /**
     * Create a new product in the system.
     * This endpoint creates a new product and returns
     * the created product's details along with relevant information.
     *
     * @param productCatalogRequestDTO the registration data for the product
     * @return a {@link ResponseEntity} containing the created user and HTTP status 201 (Created)
     */
    @PostMapping("/addProduct")
    public ResponseEntity<ProductCatalogResponseDTO> addProduct(@RequestBody ProductCatalogRequestDTO productCatalogRequestDTO) {

        var productResponse=productCatalogServiceAdmin.addProductCatalog(productCatalogRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productResponse);

    }
    /**
     * Delete new product in the system.
     *
     * @param productId the id for the product
     * @return void
     */

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productCatalogServiceAdmin.deleteProductCatalog(productId);
        return ResponseEntity.noContent().build();
    }
/**
 * Update product in the system.
 *
 * @param productId the id for the product
 *
 * @return void
 */
    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductCatalogResponseDTO> updateProduct(@PathVariable UUID productId,
                                                                   @RequestBody ProductCatalogRequestDTO productCatalogRequestDTO) {
       var updatedProduct= productCatalogServiceAdmin.updateProductCatalog(productCatalogRequestDTO, productId);
        return ResponseEntity.ok(updatedProduct);
    }
}
