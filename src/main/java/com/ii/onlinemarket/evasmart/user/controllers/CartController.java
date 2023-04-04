package com.ii.onlinemarket.evasmart.user.controllers;

import com.ii.onlinemarket.evasmart.user.payload.CartDTO;
import com.ii.onlinemarket.evasmart.user.payload.CartStatusDTO;
import com.ii.onlinemarket.evasmart.user.services.CartService;
import com.ii.onlinemarket.evasmart.user.utils.CartStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;
    @PostMapping
    public ResponseEntity<CartDTO> createCart() {
        CartDTO cartDTO = cartService.createCart();
        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable("id") Long id) {
        CartDTO cartDTO = cartService.getCartById(id);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartDTO> addItemToCart(@PathVariable("cartId") Long cartId,
                                                 @RequestParam("productId") Long productId,
                                                 @RequestParam("quantity") int quantity) {
        CartDTO cartDTO = cartService.addItemToCart(cartId, productId, quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable("cartItemId") Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{cartId}/status")
    public ResponseEntity<CartDTO> updateCartStatus(@PathVariable("cartId") Long cartId,
                                                    @RequestBody CartStatusDTO cartStatusDTO) {
        CartStatus status = CartStatus.valueOf(cartStatusDTO.getStatus());
        CartDTO cartDTO = cartService.updateCartStatus(cartId, status);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }
}
