package com.mayank.skillsell.dto_and_mapper;

public record OrderItemDto(
        Integer quantity,
        Double price,
        ProductDto productDto
) {
}
