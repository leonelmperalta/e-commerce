package com.lperalta.ecommerce.infraestructure.in.controller;

import com.lperalta.ecommerce.application.exception.NotFoundException;
import com.lperalta.ecommerce.application.service.CartService;
import com.lperalta.ecommerce.infraestructure.contants.ECommerceControllerConstants;
import com.lperalta.ecommerce.infraestructure.in.dto.CreateCartDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.CreateCartResponseDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.DeleteCartResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ECommerceControllerConstants.BASE_URL)
public class ECommerceController {

    private final CartService cartService;

    @Autowired
    public ECommerceController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(ECommerceControllerConstants.CART_URL)
    public ResponseEntity<CreateCartResponseDTO> createCart(@RequestBody CreateCartDTO cart) {
        return ResponseEntity.ok(cartService.createCart(cart));
    }

    @DeleteMapping(ECommerceControllerConstants.CART_URL)
    public ResponseEntity<DeleteCartResponseDTO> deleteCart(@RequestParam("id") Long id) throws NotFoundException {
        return ResponseEntity.ok(cartService.deleteCart(id));
    }
}
