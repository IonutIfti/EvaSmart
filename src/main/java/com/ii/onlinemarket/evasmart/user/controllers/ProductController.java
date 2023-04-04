package com.ii.onlinemarket.evasmart.user.controllers;

import com.ii.onlinemarket.evasmart.user.payload.ProductDTO;
import com.ii.onlinemarket.evasmart.user.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOList = productService.getAllProducts();
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id) {
        ProductDTO productDTO = productService.getProductById(id);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }
}
