package com.lperalta.ecommerce.application.port.out;

import com.lperalta.ecommerce.application.constants.CartServiceConstants;
import com.lperalta.ecommerce.domain.model.Cart;
import com.lperalta.ecommerce.domain.model.Product;
import com.lperalta.ecommerce.infraestructure.out.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartResponseMapper {

    public CartResponseDTO toCartResponse(Cart savedCart, CartServiceConstants.CartStatus status) {
        CartResponseDTO createCartResponse = new CartResponseDTO();
        createCartResponse.setId(savedCart.getId());
        createCartResponse.setStatus(status);
        return createCartResponse;
    }

    public CartStatusDTO toCartStatusResponse(Cart cart) {
        CartStatusDTO cartStatusDTO = new CartStatusDTO();
        cartStatusDTO.setId(cart.getId());
        cartStatusDTO.setProducts(this.toProductResponse(cart.getProductList()));
        return cartStatusDTO;
    }

    private List<ProductResponseDTO> toProductResponse(List<Product> productList) {
        return productList.stream().map(this::toProductResponseDTO).collect(Collectors.toList());
    }

    private ProductResponseDTO toProductResponseDTO(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setQuantity(product.getQuantity());
        productResponseDTO.setUnitPrice(product.getUnitPrice());
        return productResponseDTO;
    }

    public PurchasesDTO toPurchasesResponse(List<Cart> carts) {
        PurchasesDTO purchases = new PurchasesDTO();
        purchases.setPurchases(carts.stream().map(this::toPurchaseResponseDTO).collect(Collectors.toList()));
        return purchases;
    }

    public PurchaseDTO toPurchaseResponseDTO(Cart cart) {
        PurchaseDTO purchase = new PurchaseDTO();
        purchase.setDni(cart.getDni());
        purchase.setAmount(cart.getAmount());
        purchase.setDiscount(cart.getDiscount());
        purchase.setFinalAmount(cart.getFinalAmount());
        purchase.setProducts(this.toProductResponse(cart.getProductList()));
        return purchase;
    }
}
