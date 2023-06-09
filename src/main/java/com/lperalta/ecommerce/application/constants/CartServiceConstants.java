package com.lperalta.ecommerce.application.constants;

public class CartServiceConstants {

    public CartServiceConstants() {
        throw new IllegalStateException("Utility constants class.");
    }

    public enum CartStatus {
        CREATED,
        DELETED,
        PRODUCT_ADDED,
        PRODUCT_DELETED
    }

    public static final String DATE_PATTERN = "yyyy-MM-dd";

}
