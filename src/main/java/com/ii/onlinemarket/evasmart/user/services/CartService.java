package com.ii.onlinemarket.evasmart.user.services;

import com.ii.onlinemarket.evasmart.user.payload.CartDTO;
import com.ii.onlinemarket.evasmart.user.utils.CartStatus;

public interface CartService {
    CartDTO createCart();

    CartDTO getCartById(Long cartId);

    CartDTO addItemToCart(Long cartId, Long productId, int quantity);

    void removeItemFromCart(Long cartItemId);

    CartDTO updateCartStatus(Long cartId, CartStatus status);
}
