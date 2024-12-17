package com.mayank.skillsell.service;

import com.mayank.skillsell.entity.Order;
import com.mayank.skillsell.enums.OrderStatus;
import com.mayank.skillsell.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order order) {
        Order existingOrder = getOrderById(id);
        existingOrder.setOrderStatus(order.getOrderStatus());
        existingOrder.setTotalPrice(order.getTotalPrice());
        existingOrder.setOrderItems(order.getOrderItems());
        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> findOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> findOrdersByStatus(String status) {
        return orderRepository.findByOrderStatus(OrderStatus.valueOf(status));
    }
}