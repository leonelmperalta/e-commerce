package com.lperalta.ecommerce.application.service.impl;

import com.lperalta.ecommerce.application.constants.CartServiceConstants;
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
import com.lperalta.ecommerce.infraestructure.in.dto.QueryPurchaseDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.CartResponseDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.CartStatusDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.PurchaseDTO;
import com.lperalta.ecommerce.infraestructure.out.dto.PurchasesDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public CartResponseDTO createCart(CreateCartDTO cartDTO) {
        Cart cart = this.cartRequestMapper.toCart(cartDTO);
        Cart savedCart = this.cartRepository.save(cart);
        return this.cartResponseMapper.toCartResponse(savedCart, CartServiceConstants.CartStatus.CREATED);
    }

    @Override
    @Transactional
    public CartResponseDTO deleteCart(Long id) throws NotFoundException {
        Optional<Cart> cart = this.cartRepository.findById(id);
        if (cart.isEmpty()) throw new NotFoundException();
        this.cartRepository.delete(cart.get());
        return this.cartResponseMapper.toCartResponse(cart.get(), CartServiceConstants.CartStatus.DELETED);
    }

    @Override
    @Transactional
    public CartResponseDTO addProduct(ProductDTO productDTO) throws NotFoundException, DuplicatedProductException {
        Optional<Cart> cart = this.cartRepository.findById(productDTO.getCartId());
        if (cart.isEmpty()) throw new NotFoundException();
        Cart actualCart = cart.get();
        Product productFound = this.findProductInCart(actualCart, productDTO);
        if (productFound != null) throw new DuplicatedProductException();
        actualCart.addProduct(this.cartRequestMapper.toProduct(productDTO));
        this.cartRepository.save(actualCart);
        return this.cartResponseMapper.toCartResponse(actualCart, CartServiceConstants.CartStatus.PRODUCT_ADDED);
    }

    @Override
    @Transactional
    public CartResponseDTO deleteProduct(ProductDTO productDTO) throws NotFoundException {
        Optional<Cart> cart = this.cartRepository.findById(productDTO.getCartId());
        if (cart.isEmpty()) throw new NotFoundException();
        Product productFound = this.findProductInCart(cart.get(), productDTO);
        if (productFound == null) throw new NotFoundException();
        cart.get().removeProduct(productFound);
        return this.cartResponseMapper.toCartResponse(cart.get(), CartServiceConstants.CartStatus.PRODUCT_DELETED);
    }

    @Override
    public CartStatusDTO getCartStatus(Long id) throws NotFoundException {
        Optional<Cart> cart = this.cartRepository.findById(id);
        if (cart.isEmpty()) throw new NotFoundException();
        return this.cartResponseMapper.toCartStatusResponse(cart.get());
    }

    @Override
    public PurchaseDTO closeCart(Long id) throws NotFoundException, ParseException {
        Optional<Cart> cart = this.cartRepository.findByIdAndClosedFalse(id);
        if(cart.isEmpty()) throw new NotFoundException();
        Cart actualCart = cart.get();
        Double moreThanThreeProducts = this.getDiscountMoreThanThreeProducts(actualCart);
        Double specialDiscount = this.getSpecialDiscount(actualCart);
        Double forSameProducts = this.getForSameProductsDiscount(actualCart);
        Double amount = this.getCartTotal(actualCart);
        Double discount = moreThanThreeProducts + specialDiscount + forSameProducts;
        Double finalAmount = amount - discount;
        actualCart.setClosed(true);
        actualCart.setCloseDate(new Date());
        actualCart.setAmount(amount);
        actualCart.setDiscount(discount);
        actualCart.setFinalAmount(finalAmount);
        this.cartRepository.save(actualCart);
        return this.cartResponseMapper.toPurchaseResponseDTO(actualCart);
    }

    private Double getForSameProductsDiscount(Cart actualCart) {
        Double forSameProductsDiscount = 0.0;
        List<Product> products = actualCart.getProductList().stream().filter(p -> p.getQuantity() == 4).toList();
        if (!products.isEmpty()) {
            forSameProductsDiscount = products.stream().mapToDouble(Product::getUnitPrice).sum();
        }
        return forSameProductsDiscount;
    }

    private Product findProductInCart(Cart cart, ProductDTO product) {
        Optional<Product> productFound = cart.getProductList().stream().filter(
                p -> p.getName().equals(product.getName()) &&
                        p.getUnitPrice().equals(product.getUnitPrice()) &&
                        p.getQuantity().equals(product.getQuantity())
        ).findFirst();

        return productFound.orElse(null);
    }

    private Double getDiscountMoreThanThreeProducts(Cart cart) {
        Double discount = 0.0;
        List<Product> products = cart.getProductList();
        if (products.size() > 3 && cart.getSpecial()) {
            discount = 150.0;
        } else if (products.size() > 3 && !cart.getSpecial()) {
            discount = 100.0;
        }
        return discount;
    }

    private Double getSpecialDiscount(Cart cart) throws NotFoundException, ParseException {
        Double specialDiscount = 0.0;
        List<PurchaseDTO> purchases = getPurchasesOfActualMonth(cart);
        Double total = this.getTotalAmount(purchases);
        if (total > 5000 && this.getCartTotal(cart) > 2000) {
            specialDiscount = 500.0;
        }
        return specialDiscount;
    }

    private Double getCartTotal(Cart cart) {
        return cart.getProductList().stream().mapToDouble(p -> p.getUnitPrice() * p.getQuantity()).sum();
    }

    private List<PurchaseDTO> getPurchasesOfActualMonth(Cart cart) throws ParseException {
        Date dateOfActualMonthFirstDay = this.getDateOfActualMonthFirstDay();
        Date dateOfActualMonthLastDay = this.getDateOfActualMonthLastDay();
        QueryPurchaseDTO queryPurchase = new QueryPurchaseDTO(
                cart.getDni(),
                this.cartRequestMapper.toString(dateOfActualMonthFirstDay),
                this.cartRequestMapper.toString(dateOfActualMonthLastDay)
        );
        try {
            return this.getAllPurchases(queryPurchase).getPurchases();
        } catch (NotFoundException ex) {
            return new ArrayList<>();
        }
    }

    private Date getDateOfActualMonthFirstDay() {
        LocalDate firstOfMonth = LocalDate.now().withDayOfMonth(1);
        return Date.from(firstOfMonth.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date getDateOfActualMonthLastDay() {
        LocalDate lastOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        return Date.from(lastOfMonth.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public PurchasesDTO getAllPurchases(QueryPurchaseDTO queryPurchase) throws ParseException, NotFoundException {
        List<Cart> carts = this.findAllPurchases(queryPurchase);
        if(carts.isEmpty()) throw new NotFoundException();
        return this.cartResponseMapper.toPurchasesResponse(carts);
    }

    private List<Cart> findAllPurchases(QueryPurchaseDTO queryPurchase) throws ParseException {
        List<Cart> carts;
        Date dateFrom = this.cartRequestMapper.toDate(queryPurchase.getDateFrom());
        Date dateTo = this.cartRequestMapper.toDate(queryPurchase.getDateTo());
        if (dateTo != null) {
            carts = this.cartRepository.findByDniAndClosedTrueAndCloseDateGreaterThanAndCloseDateLessThan(queryPurchase.getDni(), dateFrom, dateTo);
        } else {
            carts = this.cartRepository.findByDniAndClosedTrueAndCloseDateGreaterThan(queryPurchase.getDni(), dateFrom);
        }
        return carts;
    }

    private Double getTotalAmount(List<PurchaseDTO> purchases) {
        return purchases.stream().mapToDouble(PurchaseDTO::getFinalAmount).sum();
    }
}
