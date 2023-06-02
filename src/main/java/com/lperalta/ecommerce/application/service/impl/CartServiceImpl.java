package com.lperalta.ecommerce.application.service.impl;

import com.lperalta.ecommerce.application.constants.CartServiceConstants;
import com.lperalta.ecommerce.application.exception.CartExistsException;
import com.lperalta.ecommerce.application.exception.DuplicatedProductException;
import com.lperalta.ecommerce.application.exception.NotFoundException;
import com.lperalta.ecommerce.application.port.in.CartRequestMapper;
import com.lperalta.ecommerce.application.port.out.CartResponseMapper;
import com.lperalta.ecommerce.application.service.CartService;
import com.lperalta.ecommerce.domain.model.Cart;
import com.lperalta.ecommerce.domain.model.Product;
import com.lperalta.ecommerce.domain.repository.CartRepository;
import com.lperalta.ecommerce.infraestructure.in.dto.CreateCartDTO;
import com.lperalta.ecommerce.infraestructure.in.dto.ProductDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.CartResponseDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.CartStatusDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartRequestMapper cartRequestMapper;
    private final CartResponseMapper cartResponseMapper;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository,
                           CartRequestMapper cartRequestMapper,
                           CartResponseMapper cartResponseMapper) {
        this.cartRepository = cartRepository;
        this.cartRequestMapper = cartRequestMapper;
        this.cartResponseMapper = cartResponseMapper;
    }

    @Override
    @Transactional
    public CartResponseDTO createCart(CreateCartDTO cartDTO) throws CartExistsException {
        Cart created = this.cartRepository.findByDni(cartDTO.getDni());
        if (created != null) throw new CartExistsException();
        Cart cart = this.cartRequestMapper.toCart(cartDTO);
        Cart savedCart = this.cartRepository.save(cart);
        return this.cartResponseMapper.toCartResponse(savedCart, CartServiceConstants.CartStatus.CREATED);
    }

    @Override
    @Transactional
    public CartResponseDTO deleteCart(Long dni) throws NotFoundException {
        Cart cart = this.cartRepository.findByDni(dni);
        if (cart == null) throw new NotFoundException();
        this.cartRepository.delete(cart);
        return this.cartResponseMapper.toCartResponse(cart, CartServiceConstants.CartStatus.DELETED);
    }

    @Override
    @Transactional
    public CartResponseDTO addProduct(ProductDTO productDTO) throws NotFoundException, DuplicatedProductException {
        Cart cart = this.cartRepository.findByDni(productDTO.getCartDni());
        if (cart == null ) throw new NotFoundException();
        Product productFound = this.findProductInCart(cart, productDTO);
        if (productFound != null) throw new DuplicatedProductException();
        cart.addProduct(this.cartRequestMapper.toProduct(productDTO));
        this.cartRepository.save(cart);
        return this.cartResponseMapper.toCartResponse(cart, CartServiceConstants.CartStatus.PRODUCT_ADDED);
    }

    @Override
    public CartResponseDTO deleteProduct(ProductDTO productDTO) throws NotFoundException {
        Cart cart = this.cartRepository.findByDni(productDTO.getCartDni());
        if (cart == null ) throw new NotFoundException();
        Product productFound = this.findProductInCart(cart, productDTO);
        if (productFound == null) throw new NotFoundException();
        cart.removeProduct(productFound);
        return this.cartResponseMapper.toCartResponse(cart, CartServiceConstants.CartStatus.PRODUCT_DELETED);
    }

    @Override
    public CartStatusDTO getCartStatus(Long dni) throws NotFoundException {
        Cart cart = this.cartRepository.findByDni(dni);
        if (cart == null) throw new NotFoundException();
        return this.cartResponseMapper.toCartStatusResponse(cart);
    }

    private Product findProductInCart(Cart cart, ProductDTO product) {
        Optional<Product> productFound = cart.getProductList().stream().filter(
                p -> p.getName().equals(product.getName()) &&
                        p.getUnitPrice().equals(product.getUnitPrice()) &&
                        p.getQuantity().equals(product.getQuantity())
        ).findFirst();

        return productFound.orElse(null);
    }
}
