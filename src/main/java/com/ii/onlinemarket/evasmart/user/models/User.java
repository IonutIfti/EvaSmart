package com.ii.onlinemarket.evasmart.user.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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
    @NotNull
    @Size(min=3, max=50)
    private String username;

    @Column(name="email")
    @NotNull
    @Email
    private String email;

    @Column(name="password")
    @NotNull
    @Size(min=8, max=100)
    private String password;
}
