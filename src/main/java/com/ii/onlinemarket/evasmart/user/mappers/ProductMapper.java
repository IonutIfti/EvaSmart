package com.ii.onlinemarket.evasmart.user.mappers;

import com.ii.onlinemarket.evasmart.user.models.Product;
import com.ii.onlinemarket.evasmart.user.payload.ProductDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductDTO mapToDto(Product product);

    Product mapToEntity(ProductDTO productDTO);
    List<ProductDTO> listToDTO(List<Product> products);


}
