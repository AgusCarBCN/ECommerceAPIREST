package com.carnerero.agustin.ecommerceapplication.model.enums;
import java.math.BigDecimal;

public enum ShippingMethod {

    STANDARD(new BigDecimal("5")),
    EXPRESS(new BigDecimal("15")),
    SAME_DAY(new BigDecimal("20")),
    PICKUP(BigDecimal.ZERO),
    DRONE(new BigDecimal("30"));

    private final BigDecimal price;

    ShippingMethod(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}


