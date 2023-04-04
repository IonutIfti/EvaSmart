package com.ii.onlinemarket.evasmart.user.services.impl;

import com.ii.onlinemarket.evasmart.user.exceptions.ProductNotFoundException;
import com.ii.onlinemarket.evasmart.user.mappers.ProductMapper;
import com.ii.onlinemarket.evasmart.user.models.Product;
import com.ii.onlinemarket.evasmart.user.payload.ProductDTO;
import com.ii.onlinemarket.evasmart.user.repositories.ProductRepository;
import com.ii.onlinemarket.evasmart.user.services.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return productMapper.listToDTO(products);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(String.format("Product with ID %d not found", id)));
        return productMapper.mapToDto(product);
    }
}
