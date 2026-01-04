package com.carnerero.agustin.ecommerceapplication.controller.productcatalog;


import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.ProductCatalogResponseDTO;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.productcatalog.ProductCatalogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductCatalogController {

    private final ProductCatalogService productCatalogService;

    /**
     * Retrieves a product from the catalog by its unique identifier.
     * <p>
     * This endpoint returns the product details as a {@link ProductCatalogResponseDTO}.
     * The product is fetched from the {@link ProductCatalogService}.
     * <p>
     * Example request: GET /products/{productCatalogId}
     *
     * @param productCatalogId the UUID of the product to retrieve
     * @return a {@link ResponseEntity} containing the product details with HTTP status 200 (OK)
     */

    @GetMapping("/id/{productCatalogId}")
    public ResponseEntity<ProductCatalogResponseDTO> getProductCatalogById(@PathVariable UUID productCatalogId) {
        var product=productCatalogService.getProductById(productCatalogId);

        return ResponseEntity.ok(product);
    }
    /**
     * Retrieves a product from the catalog by its unique name.
     * <p>
     * This endpoint returns the product details as a {@link ProductCatalogResponseDTO}.
     * The product is fetched from the {@link ProductCatalogService}.
     * <p>
     * Example request: GET /products/{productName}
     *
     * @param productName the unique name of the product to retrieve
     * @return a {@link ResponseEntity} containing the product details with HTTP status 200 (OK)
     */

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
    /**
     * Retrieves a paginated list of products belonging to a specific category.
     * <p>
     * This endpoint allows sorting and pagination of products within the specified category.
     * <p>
     * Example request: GET /products/category?categoryId=1&field=price&desc=true&page=0
     *
     * @param categoryId the ID of the category to filter products by
     * @param field the field used for sorting (e.g., "price", "productName")
     * @param desc true for descending order, false for ascending order
     * @param page the page number to retrieve (0-based)
     * @return a {@link ResponseEntity} containing a paginated response of products
     *         within the specified category and HTTP status 200 (OK)
     */

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

    /**
     * Retrieves a paginated list of products whose price falls within the specified range.
     * <p>
     * This endpoint supports sorting and pagination, allowing clients to filter
     * products by minimum and maximum price.
     * <p>
     * Example request: GET /products/by-price?minPrice=10&maxPrice=100&field=price&desc=true&page=0
     *
     * @param minPrice the minimum price of the products to retrieve
     * @param maxPrice the maximum price of the products to retrieve
     * @param field the field used for sorting (e.g., "price", "productName")
     * @param desc true for descending order, false for ascending order
     * @param page the page number to retrieve (0-based)
     * @return a {@link ResponseEntity} containing a paginated response of products
     *         within the specified price range and HTTP status 200 (OK)
     */
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


}
