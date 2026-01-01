package com.carnerero.agustin.ecommerceapplication.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductsId {
    private Long orderId;
    private UUID productCatalogId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderProductsId that = (OrderProductsId) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(productCatalogId, that.productCatalogId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productCatalogId);
    }
}
