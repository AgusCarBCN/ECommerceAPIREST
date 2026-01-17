package com.carnerero.agustin.ecommerceapplication.util;

import java.math.BigDecimal;

public final class AppConstants {

    private AppConstants() {}


    public static final BigDecimal TAX_RATE = new BigDecimal("0.21");
    public static final String DEFAULT_LANGUAGE = "es";
    public static final String DEFAULT_CURRENCY = "EUR";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    //messages
    public static final String PENDING_REFUND = "Refund initiated and pending to confirm.";
    public static final String CONFIRM_REFUND = "Refund accepted.";
}

