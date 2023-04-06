package com.ii.onlinemarket.evasmart.user.services.impl;

import com.ii.onlinemarket.evasmart.user.exceptions.*;
import com.ii.onlinemarket.evasmart.user.mappers.CartItemMapper;
import com.ii.onlinemarket.evasmart.user.mappers.CartMapper;
import com.ii.onlinemarket.evasmart.user.models.Cart;
import com.ii.onlinemarket.evasmart.user.models.CartItem;
import com.ii.onlinemarket.evasmart.user.models.Product;
import com.ii.onlinemarket.evasmart.user.models.User;
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
import java.util.List;

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
        try {
            Cart cart = new Cart();
            cart.setStatus(CartStatus.OPEN);
            cart.setCreationDate(LocalDateTime.now());
            cart.setModificationDate(LocalDateTime.now());
            cart = cartRepository.save(cart);
            log.info("Created cart with ID: {}", cart.getId());
            return cartMapper.mapToDTO(cart);
        } catch (Exception e) {
            log.error("Error creating cart: {}", e.getMessage());
            throw new CartServiceException("Error creating cart");
        }
    }


    @Override
    public CartDTO createCart(User user) {
        try {
            Cart cart = new Cart();
            cart.setStatus(CartStatus.OPEN);
            cart.setCreationDate(LocalDateTime.now());
            cart.setModificationDate(LocalDateTime.now());
            cart.setUser(user);
            cart = cartRepository.save(cart);
            log.info("Created cart with ID: {}", cart.getId());
            return cartMapper.mapToDTO(cart);
        } catch (Exception e) {
            log.error("Error creating cart: {}", e.getMessage());
            throw new CartOperationException("Error creating cart");
        }
    }


    @Override
    public CartDTO getCartById(Long id) {
        try {
            Cart cart = cartRepository.findById(id)
                    .orElseThrow(() -> new CartNotFoundException(String.format("Cart with ID %d not found", id)));
            log.info("Found cart with ID: {}", id);
            return cartMapper.mapToDTO(cart);
        } catch (CartNotFoundException e) {
            log.warn("Cart with ID: {} - NOT FOUND", id);
            throw e;
        } catch (Exception e) {
            log.error("Error getting cart by ID: {}", e.getMessage());
            throw new CartServiceException("Error getting cart by ID");
        }
    }


    @Override
    public CartDTO addItemToCart(Long cartId, Long productId, int quantity) {
        try {
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
        } catch (CartNotFoundException e) {
            log.warn("Cart with ID: {} - NOT FOUND", cartId);
            throw e;
        } catch (ProductNotFoundException e) {
            log.warn("Product with ID: {} - NOT FOUND", productId);
            throw e;
        } catch (Exception e) {
            log.error("Error adding item to cart: {}", e.getMessage());
            throw new CartOperationException("Error adding item to cart");
        }
    }

    @Override
    public void removeItemFromCart(Long cartItemId) {
        try {
            CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() ->
                    new CartItemNotFoundException(String.format("Cart item with ID %d not found", cartItemId)));
            Cart cart = cartItem.getCart();
            cart.getItems().remove(cartItem);
            cart.setTotalPrice(cart.getTotalPrice().subtract(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))));
            cart.setModificationDate(LocalDateTime.now());
            cartRepository.save(cart);
            cartItemRepository.delete(cartItem);
            log.info("Cart item with ID: {} has been removed from cart with ID: {}", cartItemId, cart.getId());
        } catch (CartItemNotFoundException e) {
            log.warn("CartItemNotFoundException occurred while removing item from cart. Message: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception occurred while removing item from cart. Message: {}", e.getMessage());
            throw new CartOperationException("An error occurred while removing item from cart.");
        }
    }

    @Override
    public CartDTO updateCartStatus(Long cartId, CartStatus status) {
        try {
            Cart cart = cartRepository.findById(cartId)
                    .orElseThrow(() -> new CartNotFoundException(String.format("Cart with ID %d not found", cartId)));
            cart.setStatus(status);
            cart.setModificationDate(LocalDateTime.now());
            cart = cartRepository.save(cart);
            return cartMapper.mapToDTO(cart);
        } catch (CartNotFoundException e) {
            log.error("CartNotFoundException in updateCartStatus method with message: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception in updateCartStatus method with message: {}", e.getMessage());
            throw new CartServiceException("An error occurred while updating the cart status", e);
        }
    }

    @Override
    public List<Cart> getCartsByUser(User user) {
        try {
            return cartRepository.findByUser(user);
        } catch (Exception e) {
            log.error("Exception in getCartsByUser method with message: {}", e.getMessage());
            throw new CartServiceException("An error occurred while retrieving carts by user", e);
        }
    }
}
