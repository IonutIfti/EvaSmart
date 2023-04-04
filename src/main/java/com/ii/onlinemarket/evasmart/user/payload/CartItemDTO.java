package com.ii.onlinemarket.evasmart.user.payload;

import lombok.*;

import java.math.BigDecimal;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CartItemDTO {

    private Long id;
    private Long productId;
    private String productName;
    private BigDecimal price;
    private int quantity;

}
