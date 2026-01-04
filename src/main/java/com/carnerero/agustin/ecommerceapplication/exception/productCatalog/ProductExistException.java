package com.carnerero.agustin.ecommerceapplication.exception.productCatalog;

public class ProductExistException extends RuntimeException {
    public ProductExistException(String message) {
        super(message);
    }
}
