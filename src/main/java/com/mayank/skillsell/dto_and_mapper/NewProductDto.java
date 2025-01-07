package com.mayank.skillsell.dto_and_mapper;

public record NewProductDto(
        String name,
        String description,
        Double price,
        String imageUrl,
        Integer stock,
        Long categoryId
) {
}
