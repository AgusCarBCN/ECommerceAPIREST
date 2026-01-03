package com.carnerero.agustin.ecommerceapplication.exception.user;

import jdk.jshell.spi.ExecutionControl;

public class UserExistException extends RuntimeException {

    public UserExistException(String message) {
        super(message);
    }
}
