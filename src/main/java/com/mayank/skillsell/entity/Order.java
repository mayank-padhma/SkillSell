package com.mayank.skillsell.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mayank.skillsell.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double totalPrice;

    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;
    private Long createdAt; // Use camelCase for consistency

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "buyer_id") // Unique column for buyer
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id") // Unique column for seller
    private User seller;
}
