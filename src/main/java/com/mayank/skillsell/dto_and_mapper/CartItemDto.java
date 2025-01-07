package com.mayank.skillsell.dto_and_mapper;

public record CartItemDto(
        Integer quantity,
        ProductDto productDto
) {
}
