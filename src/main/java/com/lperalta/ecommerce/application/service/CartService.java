package com.lperalta.ecommerce.application.service;

import com.lperalta.ecommerce.application.exception.CartExistsException;
import com.lperalta.ecommerce.application.exception.DuplicatedProductException;
import com.lperalta.ecommerce.application.exception.NotFoundException;
import com.lperalta.ecommerce.infraestructure.in.dto.CreateCartDTO;
import com.lperalta.ecommerce.infraestructure.in.dto.ProductDTO;
import com.lperalta.ecommerce.infraestructure.in.dto.QueryPurchaseDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.CartResponseDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.CartStatusDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.PurchaseDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.PurchasesDTO;

import java.text.ParseException;

public interface CartService {
    CartResponseDTO createCart(CreateCartDTO cart) throws CartExistsException;

    CartResponseDTO deleteCart(Long id) throws NotFoundException;

    CartResponseDTO addProduct(ProductDTO product) throws NotFoundException, DuplicatedProductException;

    CartResponseDTO deleteProduct(ProductDTO productDTO) throws NotFoundException;

    CartStatusDTO getCartStatus(Long id) throws NotFoundException;

    PurchaseDTO closeCart(Long id) throws NotFoundException, ParseException;

    PurchasesDTO getAllPurchases(QueryPurchaseDTO queryPurchase) throws ParseException, NotFoundException;
}
