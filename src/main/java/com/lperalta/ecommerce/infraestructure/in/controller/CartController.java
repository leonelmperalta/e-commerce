package com.lperalta.ecommerce.infraestructure.in.controller;

import com.lperalta.ecommerce.application.exception.CartExistsException;
import com.lperalta.ecommerce.application.exception.DuplicatedProductException;
import com.lperalta.ecommerce.application.exception.NotFoundException;
import com.lperalta.ecommerce.application.service.CartService;
import com.lperalta.ecommerce.infraestructure.contants.ECommerceControllerConstants;
import com.lperalta.ecommerce.infraestructure.in.dto.CreateCartDTO;
import com.lperalta.ecommerce.infraestructure.in.dto.ProductDTO;
import com.lperalta.ecommerce.infraestructure.in.dto.QueryPurchaseDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.CartResponseDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.CartStatusDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.PurchaseDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.PurchasesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(ECommerceControllerConstants.BASE_URL)
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(ECommerceControllerConstants.CART_URL)
    public ResponseEntity<CartResponseDTO> createCart(@RequestBody CreateCartDTO cart) throws CartExistsException {
        return ResponseEntity.ok(cartService.createCart(cart));
    }

    @DeleteMapping(ECommerceControllerConstants.CART_URL)
    public ResponseEntity<CartResponseDTO> deleteCart(@RequestParam("id") Long id) throws NotFoundException {
        return ResponseEntity.ok(cartService.deleteCart(id));
    }

    @GetMapping(ECommerceControllerConstants.CART_URL)
    public ResponseEntity<CartStatusDTO> getCartStatus(@RequestParam("id") Long id) throws NotFoundException {
        return ResponseEntity.ok(cartService.getCartStatus(id));
    }

    @PostMapping(ECommerceControllerConstants.PRODUCT_URL)
    public ResponseEntity<CartResponseDTO> addProduct(@RequestBody ProductDTO product) throws NotFoundException, DuplicatedProductException {
        return ResponseEntity.ok(cartService.addProduct(product));
    }

    @DeleteMapping(ECommerceControllerConstants.PRODUCT_URL)
    public ResponseEntity<CartResponseDTO> deleteProduct(@RequestBody ProductDTO productDTO) throws NotFoundException {
        return ResponseEntity.ok(cartService.deleteProduct(productDTO));
    }

    @PostMapping(ECommerceControllerConstants.CART_CLOSE_URL)
    public ResponseEntity<PurchaseDTO> closeCart(@RequestParam("id") Long id) throws NotFoundException, ParseException {
        return ResponseEntity.ok(cartService.closeCart(id));
    }

    @GetMapping(ECommerceControllerConstants.PURCHASES_URL)
    public ResponseEntity<PurchasesDTO> getCartStatus(@RequestBody QueryPurchaseDTO queryPurchase) throws NotFoundException, ParseException {
        return ResponseEntity.ok(cartService.getAllPurchases(queryPurchase));
    }
}
