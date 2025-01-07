package com.mayank.skillsell.dto_and_mapper;

import com.mayank.skillsell.entity.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
    public Category toCategory(CategoryDto categoryDto) {
        var category = new Category();
        category.setName(categoryDto.name());
        category.setDescription(categoryDto.description());
        return category;
    }
    public Category toNewCategory(NewCategoryDto newCategoryDto) {
        var category = new Category();
        category.setName(newCategoryDto.name());
        category.setDescription(newCategoryDto.description());
        return category;
    }
}
