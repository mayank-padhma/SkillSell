package com.mayank.skillsell.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.cglib.core.Local;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;
    private String phoneNo;
    private Set<String> roles;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    private Boolean isVerified = false;
    private String verificationToken;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private Cart cart;

    @OneToMany(
            mappedBy = "buyer"
    )
    private List<Order> buyOrders;

    @OneToMany(
            mappedBy = "seller"
    )
    private List<Order> sellOrders;
}
