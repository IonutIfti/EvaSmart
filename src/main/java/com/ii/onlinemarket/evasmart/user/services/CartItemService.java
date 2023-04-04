package com.ii.onlinemarket.evasmart.user.services;

import com.ii.onlinemarket.evasmart.user.payload.CartItemDTO;

public interface CartItemService {
    CartItemDTO getCartItemById(Long id);
}
