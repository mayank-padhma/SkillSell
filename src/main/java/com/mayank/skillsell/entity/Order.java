package com.mayank.skillsell.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mayank.skillsell.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
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
    private OrderStatus orderStatus = OrderStatus.ORDER_PLACED;

    @CreationTimestamp
    private LocalDateTime createdAt; // Use camelCase for consistency

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "buyer_id") // Unique column for buyer
    private User buyer;
}
