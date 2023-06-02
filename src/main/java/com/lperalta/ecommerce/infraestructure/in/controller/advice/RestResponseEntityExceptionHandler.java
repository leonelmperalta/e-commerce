package com.lperalta.ecommerce.infraestructure.in.controller.advice;

import com.lperalta.ecommerce.application.exception.CustomException;
import com.lperalta.ecommerce.application.exception.CustomStatusException;
import com.lperalta.ecommerce.infraestructure.out.dto.ResponseErrorAdviceDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ResponseErrorAdviceDTO> handleAccessDeniedException(
            CustomException ex, WebRequest request) {
        ResponseErrorAdviceDTO resp = new ResponseErrorAdviceDTO(ex.getCode(), ex.getDescription());
        return new ResponseEntity<ResponseErrorAdviceDTO>(
                resp, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CustomStatusException.class})
    public ResponseEntity<ResponseErrorAdviceDTO> handleAccessDeniedException(
            CustomStatusException ex, WebRequest request) {
        ResponseErrorAdviceDTO resp = new ResponseErrorAdviceDTO(ex.getCode(), ex.getDescription());
        return new ResponseEntity<ResponseErrorAdviceDTO>(
                resp, new HttpHeaders(), ex.getStatus());
    }
}