package com.ii.onlinemarket.evasmart.user.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "user_generator"
    )
    @SequenceGenerator(
            name = "user_generator",
            sequenceName = "user_sequence_name",
            allocationSize = 1
    )
    @Column(name="id")
    private Long id;

    @Column(name="username")
    @NotBlank
    @Size(min=3, max=50)
    private String username;

    @Column(name="email")
    @NotBlank
    @Email
    private String email;

    @Column(name="password")
    @NotBlank
    @Size(min=8, max=100)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cart> carts = new HashSet<>();


    public void addCart(Cart cart) {
        carts.add(cart);
        cart.setUser(this);
    }

    public void removeCart(Cart cart) {
        cart.getItems().forEach(item -> item.setCart(null));
        carts.remove(cart);
        cart.setUser(null);
    }
}
