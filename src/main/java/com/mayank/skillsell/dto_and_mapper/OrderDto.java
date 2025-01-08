package com.mayank.skillsell.dto_and_mapper;

import com.mayank.skillsell.enums.OrderStatus;

import java.util.List;

public record OrderDto(
        Long id,
        Double totalPrice,

        OrderStatus orderStatus,
        Long createdAt,

        List<OrderItemDto> orderItems,
        UserDto buyer
) { }
