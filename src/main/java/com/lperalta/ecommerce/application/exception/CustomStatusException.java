package com.lperalta.ecommerce.application.exception;

import org.springframework.http.HttpStatus;

public abstract class CustomStatusException extends CustomException {

    public CustomStatusException() {
    }

    public abstract HttpStatus getStatus();
}