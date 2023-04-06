package com.ii.onlinemarket.evasmart.user.models;

import com.ii.onlinemarket.evasmart.user.utils.CartStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "cart_generator"
    )
    @SequenceGenerator(
            name = "cart_generator",
            sequenceName = "cart_sequence_name",
            allocationSize = 1
    )
    private Long id;


    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> items = new HashSet<>();

    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "modification_date")
    private LocalDateTime modificationDate;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CartStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
    }
}
