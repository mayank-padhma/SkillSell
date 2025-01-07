package com.mayank.skillsell.dto_and_mapper;

public record ProductDto(
        Long id,
        String name,
        String description,
        Double price,
        String imageUrl,
        Integer stock,
        CategoryDto category
) {
}
