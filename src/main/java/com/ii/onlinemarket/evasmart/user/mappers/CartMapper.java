package com.ii.onlinemarket.evasmart.user.mappers;

import com.ii.onlinemarket.evasmart.user.models.Cart;
import com.ii.onlinemarket.evasmart.user.payload.CartDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface CartMapper {

    CartDTO mapToDTO(Cart cart);
    Cart mapToEntity(CartDTO cartDTO);

    List<CartDTO> listToDTO(List<Cart> carts);

}
