package com.carnerero.agustin.ecommerceapplication.exception.address;

import com.carnerero.agustin.ecommerceapplication.model.entities.UserAddressEntity;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(String message) {
        super(message);
    }

}
