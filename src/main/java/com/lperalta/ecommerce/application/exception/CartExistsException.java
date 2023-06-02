package com.lperalta.ecommerce.application.exception;

import com.lperalta.ecommerce.application.constants.ExceptionsConstants;
import org.springframework.http.HttpStatus;

public class CartExistsException extends CustomStatusException{
    @Override
    public String getCode() {
        return ExceptionsConstants.CART_EXISTS_CODE;
    }

    @Override
    public String getDescription() {
        return ExceptionsConstants.CART_EXISTS_MESSAGE;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
