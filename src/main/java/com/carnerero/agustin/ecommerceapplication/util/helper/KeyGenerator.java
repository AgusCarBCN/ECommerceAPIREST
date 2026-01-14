package com.carnerero.agustin.ecommerceapplication.util.helper;


import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {
    public String keyGenerator()  {
        byte[] key = new byte[32]; // 32 bytes = 256 bits
        new SecureRandom().nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
