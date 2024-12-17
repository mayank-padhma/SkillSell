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
    private OrderStatus orderStatus;
    private Long created_at;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;
}
