package com.ii.onlinemarket.evasmart.user.services;

import com.ii.onlinemarket.evasmart.user.payload.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long id);
}
