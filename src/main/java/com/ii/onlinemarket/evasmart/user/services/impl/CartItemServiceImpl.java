package com.ii.onlinemarket.evasmart.user.services.impl;

import com.ii.onlinemarket.evasmart.user.exceptions.CartItemNotFoundException;
import com.ii.onlinemarket.evasmart.user.exceptions.CartServiceException;
import com.ii.onlinemarket.evasmart.user.mappers.CartItemMapper;
import com.ii.onlinemarket.evasmart.user.models.CartItem;
import com.ii.onlinemarket.evasmart.user.payload.CartItemDTO;
import com.ii.onlinemarket.evasmart.user.repositories.CartItemRepository;
import com.ii.onlinemarket.evasmart.user.services.CartItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor

public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    @Override
    public CartItemDTO getCartItemById(Long id) throws CartItemNotFoundException, CartServiceException {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(() ->
                new CartItemNotFoundException(String.format("Cart item with ID %d not found", id)));
        log.info("Found cartItem by ID: {}",id);
        return cartItemMapper.mapToDTO(cartItem);
    }
}
