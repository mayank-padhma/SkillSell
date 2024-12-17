package com.mayank.skillsell.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String imageUrl;

    @OneToMany(
            mappedBy = "product"
    )
    private List<CartItem> cartItems;

    @OneToMany(
            mappedBy = "product"
    )
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(
            name = "category_id"
    )
    private Category category;
}
