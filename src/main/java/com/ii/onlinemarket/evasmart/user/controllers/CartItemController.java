package com.ii.onlinemarket.evasmart.user.controllers;

import com.ii.onlinemarket.evasmart.user.payload.CartItemDTO;
import com.ii.onlinemarket.evasmart.user.services.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartitems")
@AllArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @GetMapping("/{id}")
    public ResponseEntity<CartItemDTO> getCartItemById(@PathVariable("id") Long id) {
        CartItemDTO cartItemDTO = cartItemService.getCartItemById(id);
        return new ResponseEntity<>(cartItemDTO, HttpStatus.OK);
    }
}
