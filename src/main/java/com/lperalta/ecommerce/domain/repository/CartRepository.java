package com.lperalta.ecommerce.domain.repository;

import com.lperalta.ecommerce.domain.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByDni(Long dni);
}
