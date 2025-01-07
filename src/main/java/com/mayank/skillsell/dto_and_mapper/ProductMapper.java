package com.mayank.skillsell.dto_and_mapper;

import com.mayank.skillsell.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    @Autowired
    CategoryMapper categoryMapper;

    public ProductDto toProductDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl(),
                product.getStock(),
                categoryMapper.toCategoryDto(product.getCategory())
        );
    }
}
