package com.mayank.skillsell.dto_and_mapper;

import com.mayank.skillsell.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class OrderMapper {
    @Autowired
    private OrderItemMapper orderItemMapper;
    private UserMapper userMapper;
    public OrderDto toOrderDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getTotalPrice(),
                order.getOrderStatus(),
                order.getCreatedAt(),
                order.getOrderItems().stream()
                        .map(orderItemMapper::toOrderItemDto)
                        .collect(Collectors.toList()),
                userMapper.toUserDto(order.getBuyer()),
                userMapper.toUserDto(order.getSeller())
        );
    }
}