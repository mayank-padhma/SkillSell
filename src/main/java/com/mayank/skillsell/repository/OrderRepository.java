package com.mayank.skillsell.repository;

import com.mayank.skillsell.entity.Order;
import com.mayank.skillsell.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByBuyerId(Long buyerId);
    List<Order> findBySellerId(Long sellerId);
    List<Order> findByOrderStatus(OrderStatus status);
}
