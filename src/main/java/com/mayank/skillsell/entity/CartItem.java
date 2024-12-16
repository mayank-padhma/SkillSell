package com.mayank.skillsell.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private Double price;
    @ManyToOne
    @JoinColumn(
            name = "cart_id"
    )
    @JsonBackReference
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false) // Reference to the Product table
    private Product product;
}
