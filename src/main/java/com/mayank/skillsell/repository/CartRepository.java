package com.mayank.skillsell.repository;

import com.mayank.skillsell.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);
//    Optional<Cart> findByUserIdAndProductId(Long userId, Long productId);
//    Optional<Cart> findByUser_UserId(Long userId);
}
