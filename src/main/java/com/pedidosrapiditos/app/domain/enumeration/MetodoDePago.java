package com.pedidosrapiditos.app.domain.enumeration;

/**
 * The MetodoDePago enumeration.
 */
public enum MetodoDePago {
    EFECTIVO("efectivo"),
    CUPON("cupon"),
    TARJETA("tarjeta");

    private final String value;

    MetodoDePago(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
