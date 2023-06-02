package com.lperalta.ecommerce.application.service;

import com.lperalta.ecommerce.application.exception.NotFoundException;
import com.lperalta.ecommerce.infraestructure.in.dto.CreateCartDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.CreateCartResponseDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.DeleteCartResponseDTO;

public interface CartService {
    CreateCartResponseDTO createCart(CreateCartDTO cart);

    DeleteCartResponseDTO deleteCart(Long id) throws NotFoundException;
}
