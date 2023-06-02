package com.lperalta.ecommerce.application.port.out;

import com.lperalta.ecommerce.application.constants.CartServiceConstants;
import com.lperalta.ecommerce.domain.model.Cart;
import com.lperalta.ecommerce.infraestructure.out.dto.CartResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CartResponseMapper {

    public CartResponseDTO toCartResponse(Cart savedCart, CartServiceConstants.CartStatus status) {
        CartResponseDTO createCartResponse = new CartResponseDTO();
        createCartResponse.setDni(savedCart.getDni());
        createCartResponse.setStatus(status);
        return createCartResponse;
    }
}
