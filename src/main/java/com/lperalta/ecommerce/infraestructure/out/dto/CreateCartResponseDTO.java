package com.lperalta.ecommerce.infraestructure.out.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lperalta.ecommerce.application.constants.CartServiceConstants;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateCartResponseDTO {

    private Long dni;
    private CartServiceConstants.CartStatus status;

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public CartServiceConstants.CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartServiceConstants.CartStatus status) {
        this.status = status;
    }
}
