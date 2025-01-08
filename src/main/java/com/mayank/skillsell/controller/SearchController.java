package com.mayank.skillsell.controller;

import com.mayank.skillsell.dto_and_mapper.ProductDto;
import com.mayank.skillsell.entity.Product;
import com.mayank.skillsell.repository.ProductRepository;
import com.mayank.skillsell.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@Tag(name = "Search APIs", description = "Handles the search products")
public class SearchController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDto> searchProducts(@RequestParam String name) {
        return productService.searchProductsDtoByName(name);
    }
}