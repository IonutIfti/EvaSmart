package com.ii.onlinemarket.evasmart.user.services.impl;

import com.ii.onlinemarket.evasmart.user.exceptions.ProductNotFoundException;
import com.ii.onlinemarket.evasmart.user.exceptions.ProductServiceException;
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
        try {
            List<Product> products = productRepository.findAll();
            log.info("Retrieved all products");
            return productMapper.listToDTO(products);
        } catch (Exception e) {
            log.error("Error getting all products: {}", e.getMessage());
            throw new ProductServiceException("Error getting all products");
        }
    }

    @Override
    public ProductDTO getProductById(Long id) {
        try {
            Product product = productRepository.findById(id).orElseThrow(() ->
                    new ProductNotFoundException(String.format("Product with ID %d not found", id)));
            log.info("Found product with ID: {}",id);
            return productMapper.mapToDto(product);
        } catch (ProductNotFoundException e) {
            log.warn("Product with ID: {} - NOT FOUND", id);
            throw e;
        } catch (Exception e) {
            log.error("Error getting product by ID: {}", e.getMessage());
            throw new ProductServiceException("Error getting product by ID");
        }
    }
}
