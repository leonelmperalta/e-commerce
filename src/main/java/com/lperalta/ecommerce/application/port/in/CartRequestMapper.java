package com.lperalta.ecommerce.application.port.in;

import com.lperalta.ecommerce.domain.model.Cart;
import com.lperalta.ecommerce.infraestructure.in.dto.CreateCartDTO;
import org.springframework.stereotype.Component;

@Component
public class CartRequestMapper {

    public Cart toCart(CreateCartDTO cartDTO) {
        Cart cart = new Cart();
        cart.setDni(cartDTO.getDni());
        cart.setSpecial(cartDTO.getSpecial());
        cart.setClosed(false);
        return cart;
    }
}

