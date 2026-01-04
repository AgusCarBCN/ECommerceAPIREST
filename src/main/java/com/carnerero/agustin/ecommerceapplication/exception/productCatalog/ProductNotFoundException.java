package com.carnerero.agustin.ecommerceapplication.exception.productCatalog;

public class ProductNotFoundException extends  RuntimeException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
