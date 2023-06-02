package com.lperalta.ecommerce.application.port.in;

import com.lperalta.ecommerce.domain.model.Cart;
import com.lperalta.ecommerce.infraestructure.in.dto.CreateCartDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.CreateCartResponseDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.DeleteCartResponseDTO;
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

    public CreateCartResponseDTO toCartCreateResponse(Cart savedCart) {
        CreateCartResponseDTO createCartResponse = new CreateCartResponseDTO();
        createCartResponse.setId(savedCart.getId());
        return createCartResponse;
    }

    public DeleteCartResponseDTO toDeleteCartResponseDTO(Long id) {
        DeleteCartResponseDTO deleteCartResponseDTO = new DeleteCartResponseDTO();
        deleteCartResponseDTO.setId(id);
        return deleteCartResponseDTO;
    }
}

