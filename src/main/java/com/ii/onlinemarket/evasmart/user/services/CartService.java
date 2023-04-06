package com.ii.onlinemarket.evasmart.user.services;

import com.ii.onlinemarket.evasmart.user.models.Cart;
import com.ii.onlinemarket.evasmart.user.models.User;
import com.ii.onlinemarket.evasmart.user.payload.CartDTO;
import com.ii.onlinemarket.evasmart.user.utils.CartStatus;

import java.util.List;

public interface CartService {

    CartDTO createCart();

    CartDTO createCart(User user);

    CartDTO getCartById(Long cartId);

    CartDTO addItemToCart(Long cartId, Long productId, int quantity);

    void removeItemFromCart(Long cartItemId);

    CartDTO updateCartStatus(Long cartId, CartStatus status);

    List<Cart> getCartsByUser(User user);

}

