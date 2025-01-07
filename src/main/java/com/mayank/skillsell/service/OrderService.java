package com.mayank.skillsell.service;

import com.mayank.skillsell.dto_and_mapper.OrderDto;
import com.mayank.skillsell.dto_and_mapper.OrderMapper;
import com.mayank.skillsell.entity.Order;
import com.mayank.skillsell.enums.OrderStatus;
import com.mayank.skillsell.exceptions.InvalidOrderStatusException;
import com.mayank.skillsell.exceptions.OrderNotFoundException;
import com.mayank.skillsell.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * Fetch all orders.
     */
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public List<OrderDto> getOrdersDto() {
        return orderRepository.findAll()
                .stream().map(orderMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    /**
     * Fetch all orders placed by a buyer.
     */
    public List<Order> getOrdersByBuyerId(Long buyerId) {
        return orderRepository.findByBuyerId(buyerId);
    }

    public List<OrderDto> getOrdersDtoByBuyerId(Long buyerId) {
        return orderRepository.findByBuyerId(buyerId)
                .stream().map(orderMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    /**
     * Fetch all orders placed by a seller.
     */
    public List<Order> getOrdersBySellerId(Long sellerId) {
        return orderRepository.findBySellerId(sellerId);
    }

    public List<OrderDto> getOrdersDtoBySellerId(Long sellerId) {
        return orderRepository.findBySellerId(sellerId)
                .stream().map(orderMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    /**
     * Fetch order by its ID.
     */
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }
    public OrderDto getOrderDtoById(Long id) {
        return orderMapper.toOrderDto(orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id)));
    }

    /**
     * Create a new order.
     */
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    /**
     * Update an existing order.
     */
    public Order updateOrder(Long id, Order order) {
        Order existingOrder = getOrderById(id);
        existingOrder.setOrderStatus(order.getOrderStatus());
        existingOrder.setTotalPrice(order.getTotalPrice());
        existingOrder.setOrderItems(order.getOrderItems());
        return orderRepository.save(existingOrder);
    }

    /**
     * Delete an order by ID.
     */
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    /**
     * Fetch orders by status.
     */
    public List<Order> findOrdersByStatus(String status) {
        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
            return orderRepository.findByOrderStatus(orderStatus);
        } catch (IllegalArgumentException e) {
            throw new InvalidOrderStatusException("Invalid order status: " + status);
        }
    }
}

