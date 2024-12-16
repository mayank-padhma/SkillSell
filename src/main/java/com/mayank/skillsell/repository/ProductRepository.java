package com.mayank.skillsell.repository;

import com.mayank.skillsell.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByNameContaining(String name);
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
}