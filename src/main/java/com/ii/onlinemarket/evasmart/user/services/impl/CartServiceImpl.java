package com.ii.onlinemarket.evasmart.user.services.impl;

import com.ii.onlinemarket.evasmart.user.exceptions.*;
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
    private final ProductRepository productRepository;

    @Override
    public CartDTO createCart() throws CartServiceException {
        Cart cart = Cart.builder()
                .status(CartStatus.OPEN)
                .creationDate(LocalDateTime.now())
                .modificationDate(LocalDateTime.now())
                .build();

        if (cart.getId() == null) {
            log.error("Error creating cart: Cart ID is null");
            throw new CartServiceException("Error creating cart");
        }
        cart = cartRepository.save(cart);
        log.info("Created cart with ID: {}", cart.getId());
        return cartMapper.mapToDTO(cart);
    }

    @Override
    public CartDTO createCart(User user) throws CartOperationException {
        Cart cart = Cart.builder()
                .status(CartStatus.OPEN)
                .creationDate(LocalDateTime.now())
                .modificationDate(LocalDateTime.now())
                .user(user)
                .build();

        if (cart.getId() == null) {
            log.error("Error creating cart: Cart ID is null");
            throw new CartOperationException("Error creating cart");
        }

        cart = cartRepository.save(cart);
        log.info("Created cart with ID: {}", cart.getId());
        return cartMapper.mapToDTO(cart);
    }

    @Override
    public CartDTO getCartById(Long id) throws CartNotFoundException, CartServiceException {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException(String.format("Cart with ID %d not found", id)));

        log.info("Found cart with ID: {}", id);
        return cartMapper.mapToDTO(cart);
    }

    @Override
    public CartDTO addItemToCart(Long cartId, Long productId, int quantity)
            throws CartNotFoundException, ProductNotFoundException, CartOperationException {
        Cart cart = cartRepository.findByIdAndStatus(cartId, CartStatus.OPEN)
                .orElseThrow(() -> new CartNotFoundException(
                        String.format("Open cart with ID %d not found", cartId)));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Product with ID %d not found", productId)));

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .price(product.getPrice())
                .quantity(quantity)
                .build();

        cartItem = cartItemRepository.save(cartItem);

        cart.getItems().add(cartItem);
        cart.setTotalPrice(cart.getTotalPrice().add(cartItem.getPrice().multiply(BigDecimal.valueOf(quantity))));
        cart.setModificationDate(LocalDateTime.now());
        cart = cartRepository.save(cart);

        return cartMapper.mapToDTO(cart);
    }

    @Override
    public void removeItemFromCart(Long cartItemId) throws CartItemNotFoundException, CartOperationException {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() ->
                new CartItemNotFoundException(String.format("Cart item with ID %d not found", cartItemId)));

        Cart cart = cartItem.getCart();
        cart.getItems().remove(cartItem);
        cart.setTotalPrice(cart.getTotalPrice().subtract(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))));
        cart.setModificationDate(LocalDateTime.now());
        cartRepository.save(cart);
        cartItemRepository.delete(cartItem);

        log.info("Cart item with ID: {} has been removed from cart with ID: {}", cartItemId, cart.getId());
    }

    @Override
    public CartDTO updateCartStatus(Long cartId, CartStatus status) throws CartNotFoundException, CartServiceException {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(String.format("Cart with ID %d not found", cartId)));

        cart.setStatus(status);
        cart.setModificationDate(LocalDateTime.now());
        cart = cartRepository.save(cart);
        log.info("Cart with ID: {}, status updated", cartId);
        return cartMapper.mapToDTO(cart);
    }

    @Override
    public List<Cart> getCartsByUser(User user) throws CartServiceException {
        log.info("Found cart by user ID: {}", user.getId());
        return cartRepository.findByUser(user);
    }
}
