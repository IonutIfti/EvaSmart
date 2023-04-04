package com.ii.onlinemarket.evasmart.user.services.impl;

import com.ii.onlinemarket.evasmart.user.exceptions.CartItemNotFoundException;
import com.ii.onlinemarket.evasmart.user.exceptions.CartNotFoundException;
import com.ii.onlinemarket.evasmart.user.exceptions.ProductNotFoundException;
import com.ii.onlinemarket.evasmart.user.mappers.CartItemMapper;
import com.ii.onlinemarket.evasmart.user.mappers.CartMapper;
import com.ii.onlinemarket.evasmart.user.models.Cart;
import com.ii.onlinemarket.evasmart.user.models.CartItem;
import com.ii.onlinemarket.evasmart.user.models.Product;
import com.ii.onlinemarket.evasmart.user.payload.CartDTO;
import com.ii.onlinemarket.evasmart.user.repositories.CartItemRepository;
import com.ii.onlinemarket.evasmart.user.repositories.CartRepository;
import com.ii.onlinemarket.evasmart.user.repositories.ProductRepository;
import com.ii.onlinemarket.evasmart.user.services.CartService;
import com.ii.onlinemarket.evasmart.user.utils.CartStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final ProductRepository productRepository;
    @Override
    public CartDTO createCart() {
        Cart cart = new Cart();
        cart.setStatus(CartStatus.OPEN);
        cart.setCreationDate(LocalDateTime.now());
        cart.setModificationDate(LocalDateTime.now());
        cart = cartRepository.save(cart);
        return cartMapper.mapToDTO(cart);
    }

    @Override
    public CartDTO getCartById(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() ->
                new CartNotFoundException(String.format("Cart with ID %d not found", id)));
        return cartMapper.mapToDTO(cart);
    }

    @Override
    public CartDTO addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findByIdAndStatus(cartId, CartStatus.OPEN)
                .orElseThrow(() -> new CartNotFoundException(
                        String.format("Open cart with ID %d not found", cartId)));
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ProductNotFoundException(String.format("Product with ID %d not found", productId)));
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setPrice(product.getPrice());
        cartItem.setQuantity(quantity);
        cartItem = cartItemRepository.save(cartItem);
        cart.getItems().add(cartItem);
        cart.setTotalPrice(cart.getTotalPrice().add(cartItem.getPrice().multiply(BigDecimal.valueOf(quantity))));
        cart.setModificationDate(LocalDateTime.now());
        cart = cartRepository.save(cart);
        return cartMapper.mapToDTO(cart);
    }

    @Override
    public void removeItemFromCart(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() ->
                new CartItemNotFoundException(String.format("Cart item with ID %d not found", cartItemId)));
        Cart cart = cartItem.getCart();
        cart.getItems().remove(cartItem);
        cart.setTotalPrice(cart.getTotalPrice().subtract(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))));
        cart.setModificationDate(LocalDateTime.now());
        cartRepository.save(cart);
        cartItemRepository.delete(cartItem);
    }

    @Override
    public CartDTO updateCartStatus(Long cartId, CartStatus status) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                new CartNotFoundException(String.format("Cart with ID %d not found", cartId)));
        cart.setStatus(status);
        cart.setModificationDate(LocalDateTime.now());
        cart = cartRepository.save(cart);
        return cartMapper.mapToDTO(cart);
    }
}
