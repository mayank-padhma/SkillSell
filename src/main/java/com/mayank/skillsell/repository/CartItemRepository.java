package com.mayank.skillsell.repository;

import com.mayank.skillsell.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository  extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartId(Long cartId);
}