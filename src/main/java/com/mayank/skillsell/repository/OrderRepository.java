package com.mayank.skillsell.repository;

import com.mayank.skillsell.entity.Order;
import com.mayank.skillsell.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByOrderStatus(OrderStatus status);
}
