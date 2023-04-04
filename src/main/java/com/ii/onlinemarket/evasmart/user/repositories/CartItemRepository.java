package com.ii.onlinemarket.evasmart.user.repositories;

import com.ii.onlinemarket.evasmart.user.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
