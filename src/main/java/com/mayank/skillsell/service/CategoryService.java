package com.mayank.skillsell.service;

import com.mayank.skillsell.dto_and_mapper.CategoryDto;
import com.mayank.skillsell.dto_and_mapper.CategoryMapper;
import com.mayank.skillsell.dto_and_mapper.NewCategoryDto;
import com.mayank.skillsell.entity.Category;
import com.mayank.skillsell.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream().map(categoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    public CategoryDto getCategoryDtoById(Long id) {
        return categoryMapper.toCategoryDto(categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id)));
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        return categoryMapper.toCategoryDto(categoryRepository.save(categoryMapper.toNewCategory(newCategoryDto)));
    }

    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category existingCategory = getCategoryById(id);
        existingCategory.setName(categoryDto.name());
        return categoryMapper.toCategoryDto(categoryRepository.save(existingCategory));
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }
}