package com.lperalta.ecommerce.application.port.in;

import com.lperalta.ecommerce.application.constants.CartServiceConstants;
import com.lperalta.ecommerce.domain.model.Cart;
import com.lperalta.ecommerce.domain.model.Product;
import com.lperalta.ecommerce.infraestructure.in.dto.CreateCartDTO;
import com.lperalta.ecommerce.infraestructure.in.dto.ProductDTO;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public Date toDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(CartServiceConstants.DATE_PATTERN);
        return date != null ? sdf.parse(date) : null;
    }

    public String toString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(CartServiceConstants.DATE_PATTERN);
        return sdf.format(date);
    }
}

