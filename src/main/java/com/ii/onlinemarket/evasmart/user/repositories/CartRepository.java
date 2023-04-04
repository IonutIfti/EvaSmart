package com.ii.onlinemarket.evasmart.user.repositories;

import com.ii.onlinemarket.evasmart.user.models.Cart;
import com.ii.onlinemarket.evasmart.user.utils.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByIdAndStatus(Long cartId, CartStatus cartStatus);
}
