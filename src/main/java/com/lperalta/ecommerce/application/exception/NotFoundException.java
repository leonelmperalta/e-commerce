package com.lperalta.ecommerce.application.exception;

import com.lperalta.ecommerce.application.constants.ExceptionsConstants;
import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomStatusException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getCode() {
        return ExceptionsConstants.NOT_FOUND_CODE;
    }

    @Override
    public String getDescription() {
        return ExceptionsConstants.NOT_FOUND_MESSAGE;
    }
}
