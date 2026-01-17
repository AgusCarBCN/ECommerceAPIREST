package com.carnerero.agustin.ecommerceapplication.controller;


import com.carnerero.agustin.ecommerceapplication.dtos.requests.ProductCatalogRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.ProductCatalogResponseDTO;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.ProductCatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
@PreAuthorize("permitAll()")
public class ProductCatalogController {

    private final ProductCatalogService productCatalogService;

    // ---------------------------
    // Get product by ID
    // ---------------------------
    @Operation(
            summary = "Get product by ID",
            description = "Returns a single product from the catalog by its UUID",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping("/id/{productCatalogId}")
    public ResponseEntity<ProductCatalogResponseDTO> getProductCatalogById(@PathVariable UUID productCatalogId) {
        var product=productCatalogService.getProductById(productCatalogId);

        return ResponseEntity.ok(product);
    }

    // ---------------------------
    // Search products by name
    // ---------------------------
    @Operation(
            summary = "Search products by name",
            description = "Returns paginated products whose names match the search query",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping("/by-productName")
    public ResponseEntity<PageResponse<ProductCatalogResponseDTO>> getProductCatalogByName(
            @RequestParam String productName,
            @RequestParam String field,
            @RequestParam boolean desc,
            @RequestParam Integer page
    ) {
        var product=productCatalogService.searchProductsByName(productName,field,desc,page);
        return ResponseEntity.ok(product);
    }

    // ---------------------------
    // Get products by category
    // ---------------------------
    @Operation(
            summary = "Get products by category",
            description = "Returns paginated products belonging to a given category",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping("/category")
    public ResponseEntity<PageResponse<ProductCatalogResponseDTO>> getProductsByCategory(
            @RequestParam Long categoryId,
            @RequestParam String field,
            @RequestParam boolean desc,
            @RequestParam Integer page) {
        var products=productCatalogService.getProductsByCategory(
                categoryId,
                field,
                desc,
                page);
        return ResponseEntity.ok(products);
    }

    // ---------------------------
    // Get products by price range
    // ---------------------------
    @Operation(
            summary = "Get products by price range",
            description = "Returns paginated products within the specified min and max price",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping("/by-price")
    public ResponseEntity<PageResponse<ProductCatalogResponseDTO>> getProductsByPrice(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice,
            @RequestParam String field,
            @RequestParam boolean desc,
            @RequestParam Integer page) {
        var products=productCatalogService.getProductsByPriceRange(
                minPrice,
                maxPrice,
                field,
                desc,
                page);
        return ResponseEntity.ok(products);
    }

    // ---------------------------
    // Add product (Admin only)
    // ---------------------------
    @Operation(
            summary = "Add a new product",
            description = "Creates a new product in the catalog. Admin access required.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "403", description = "Forbidden: Admin role required")
    })
    @PostMapping("/addProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductCatalogResponseDTO> addProduct(@RequestBody ProductCatalogRequestDTO productCatalogRequestDTO) {

        var productResponse=productCatalogService.addProductCatalog(productCatalogRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productResponse);

    }

    // ---------------------------
    // Delete product (Admin only)
    // ---------------------------
    @Operation(
            summary = "Delete a product",
            description = "Deletes a product by its UUID. Admin access required.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "403", description = "Forbidden: Admin role required")
    })
    @DeleteMapping("/delete/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productCatalogService.deleteProductCatalog(productId);
        return ResponseEntity.noContent().build();
    }

    // ---------------------------
    // Update product (Admin only)
    // ---------------------------
    @Operation(
            summary = "Update a product",
            description = "Updates a product in the catalog by UUID. Admin access required.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "403", description = "Forbidden: Admin role required")
    })
    @PutMapping("/update/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductCatalogResponseDTO> updateProduct(@PathVariable UUID productId,
                                                                   @RequestBody ProductCatalogRequestDTO productCatalogRequestDTO) {
        var updatedProduct= productCatalogService.updateProductCatalog(productCatalogRequestDTO, productId);
        return ResponseEntity.ok(updatedProduct);
    }

}
