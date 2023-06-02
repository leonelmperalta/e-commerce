package com.lperalta.ecommerce.infraestructure.out.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lperalta.ecommerce.application.constants.CartServiceConstants;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CartResponseDTO {

    private Long id;
    private CartServiceConstants.CartStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CartServiceConstants.CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartServiceConstants.CartStatus status) {
        this.status = status;
    }
}
