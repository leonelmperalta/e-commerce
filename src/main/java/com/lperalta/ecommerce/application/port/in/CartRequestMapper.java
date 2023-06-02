package com.lperalta.ecommerce.application.port.in;

import com.lperalta.ecommerce.domain.model.Cart;
import com.lperalta.ecommerce.domain.model.Product;
import com.lperalta.ecommerce.infraestructure.in.dto.CreateCartDTO;
import com.lperalta.ecommerce.infraestructure.in.dto.ProductDTO;
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

    public Product toProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setQuantity(productDTO.getQuantity());
        product.setUnitPrice(productDTO.getUnitPrice());
        return product;
    }
}

