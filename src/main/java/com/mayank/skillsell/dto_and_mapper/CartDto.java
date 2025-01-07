package com.mayank.skillsell.dto_and_mapper;

import java.util.List;

public record CartDto(
        List<CartItemDto> cartItems
) { }
