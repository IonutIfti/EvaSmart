package com.ii.onlinemarket.evasmart.user.payload;

import com.ii.onlinemarket.evasmart.user.utils.CartStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor

@Builder
public class CartDTO {
    private Long id;
    private List<CartItemDTO> items;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private BigDecimal totalPrice;
    private CartStatus status;
    public CartDTO() {
        items = new ArrayList<>();
    }
    public void addItem(CartItemDTO item) {
        items.add(item);
    }

    public void removeItem(CartItemDTO item) {
        items.remove(item);
    }
}
