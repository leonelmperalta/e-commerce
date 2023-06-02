package com.lperalta.ecommerce.domain.repository;

import com.lperalta.ecommerce.domain.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByDniAndClosedTrueAndCloseDateGreaterThan(Long dni, Date dateFrom);
    List<Cart> findByDniAndClosedTrueAndCloseDateGreaterThanAndCloseDateLessThan(Long dni, Date dateFrom, Date dateTo);
    Optional<Cart> findByIdAndClosedFalse(Long id);

}
