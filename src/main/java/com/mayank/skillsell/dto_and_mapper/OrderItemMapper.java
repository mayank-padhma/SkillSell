package com.mayank.skillsell.dto_and_mapper;

import com.mayank.skillsell.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemMapper {
    @Autowired
    ProductMapper productMapper;
    public OrderItemDto toOrderItemDto(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.getQuantity(),
                orderItem.getPrice(),
                productMapper.toProductDto(orderItem.getProduct())
        );
    }
}
