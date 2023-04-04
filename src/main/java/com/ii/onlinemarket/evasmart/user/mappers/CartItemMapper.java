package com.ii.onlinemarket.evasmart.user.mappers;

import com.ii.onlinemarket.evasmart.user.models.CartItem;
import com.ii.onlinemarket.evasmart.user.payload.CartItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CartMapper.class, ProductMapper.class})
public interface CartItemMapper {

    @Mappings({
            @Mapping(source = "cart.id", target = "id"),
            @Mapping(source = "product.id", target = "productId"),
            @Mapping(source = "product.name", target = "productName")
    })
    CartItemDTO mapToDTO(CartItem cartItem);

    @Mappings({
            @Mapping(source = "id", target = "cart.id"),
            @Mapping(source = "productId", target = "product.id")
    })
    CartItem mapToEntity(CartItemDTO cartItemDTO);

    List<CartItemDTO> listToDTO(List<CartItem> cartItems);

    List<CartItem> listToEntity(List<CartItemDTO> cartItemDTOs);
}
