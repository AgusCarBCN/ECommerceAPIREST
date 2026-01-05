package com.carnerero.agustin.ecommerceapplication.model.enums;

public enum BillStatus {
    ACTIVE,     // Factura válida generada
    CANCELLED,  // Factura anulada (orden cancelada o error)
    REFUNDED    // Factura reembolsada parcial o totalmente
}
