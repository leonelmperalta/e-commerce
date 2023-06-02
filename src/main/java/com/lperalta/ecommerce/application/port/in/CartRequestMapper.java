package com.lperalta.ecommerce.application.port.in;

import com.lperalta.ecommerce.application.constants.CartServiceConstants;
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
        createCartResponse.setDni(savedCart.getDni());
        createCartResponse.setStatus(CartServiceConstants.CartStatus.CREATED);
        return createCartResponse;
    }

    public DeleteCartResponseDTO toDeleteCartResponseDTO(Long dni) {
        DeleteCartResponseDTO deleteCartResponseDTO = new DeleteCartResponseDTO();
        deleteCartResponseDTO.setDni(dni);
        deleteCartResponseDTO.setStatus(CartServiceConstants.CartStatus.DELETED);
        return deleteCartResponseDTO;
    }
}

