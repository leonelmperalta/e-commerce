package com.lperalta.ecommerce.application.service.impl;

import com.lperalta.ecommerce.application.exception.NotFoundException;
import com.lperalta.ecommerce.application.port.in.CartRequestMapper;
import com.lperalta.ecommerce.application.service.CartService;
import com.lperalta.ecommerce.domain.model.Cart;
import com.lperalta.ecommerce.domain.repository.CartRepository;
import com.lperalta.ecommerce.infraestructure.in.dto.CreateCartDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.CreateCartResponseDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.DeleteCartResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartRequestMapper cartRequestMapper;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository,
                           CartRequestMapper cartRequestMapper) {
        this.cartRepository = cartRepository;
        this.cartRequestMapper = cartRequestMapper;
    }

    @Override
    @Transactional
    public CreateCartResponseDTO createCart(CreateCartDTO cartDTO) {
        Cart cart = this.cartRequestMapper.toCart(cartDTO);
        Cart savedCart = this.cartRepository.save(cart);
        return this.cartRequestMapper.toCartCreateResponse(savedCart);
    }

    @Override
    @Transactional
    public DeleteCartResponseDTO deleteCart(Long dni) throws NotFoundException {
        if (!this.cartRepository.existsByDni(dni)) {
            throw new NotFoundException();
        }
        this.cartRepository.deleteByDni(dni);
        return this.cartRequestMapper.toDeleteCartResponseDTO(dni);
    }
}
