package com.lperalta.ecommerce.infraestructure.controller;

import com.lperalta.ecommerce.domain.model.Cart;
import com.lperalta.ecommerce.domain.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ECommerceController {

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/getCarts")
    public ResponseEntity<List<Cart>> findAll() {
        return ResponseEntity.ok(cartRepository.findAll());
    }
}
