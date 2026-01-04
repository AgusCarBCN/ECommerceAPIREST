package com.carnerero.agustin.ecommerceapplication.controller.productcatalog;


import com.carnerero.agustin.ecommerceapplication.dtos.requests.ProductCatalogRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.OrderProductResponseDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.ProductCatalogResponseDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.UserResponseDTO;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.productcatalog.ProductCatalogServiceAdmin;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/products/admin")
public class ProductCatalogAdminController {

    private final ProductCatalogServiceAdmin productCatalogServiceAdmin;

    /**
     * Create a new product in the system.
     * <p>
     * This endpoint creates a new product and returns
     * the created product's details along with relevant information.
     *
     * Example request: POST /products/r
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
}
