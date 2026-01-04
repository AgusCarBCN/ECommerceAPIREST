package com.carnerero.agustin.ecommerceapplication.services.interfaces;

import java.util.UUID;

public interface OrderProductService {

    /**
     * Add a product to an existing order.
     * @param orderId the ID of the order
     * @param productId the ID of the product
     * @param quantity quantity to add
     */
    void addProductToOrder(Long orderId, UUID productId, Long quantity);

    /**
     * Remove a product from an existing order.
     * @param orderId the ID of the order
     * @param productId the ID of the product
     */
    void removeProductFromOrder(Long orderId, UUID productId);

    /**
     * Update the quantity of a product in an existing order.
     * @param orderId the ID of the order
     * @param productId the ID of the product
     * @param newQuantity new quantity to set
     */
    void updateProductQuantity(Long orderId, UUID productId, Long newQuantity);
}
