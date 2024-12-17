package com.mayank.skillsell.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private Cart cart;

    @OneToMany(
            mappedBy = "user"
    )
    private List<Order> orders;
}
