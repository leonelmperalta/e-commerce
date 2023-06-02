package com.lperalta.ecommerce.application.service;

import com.lperalta.ecommerce.application.exception.CartExistsException;
import com.lperalta.ecommerce.application.exception.NotFoundException;
import com.lperalta.ecommerce.infraestructure.in.dto.CreateCartDTO;
import com.lperalta.ecommerce.infraestructure.in.dto.ProductDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.CartResponseDTO;

public interface CartService {
    CartResponseDTO createCart(CreateCartDTO cart) throws CartExistsException;

    CartResponseDTO deleteCart(Long dni) throws NotFoundException;

    CartResponseDTO addProduct(ProductDTO product) throws NotFoundException;
}
