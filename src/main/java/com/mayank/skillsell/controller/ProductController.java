package com.mayank.skillsell.controller;

import com.mayank.skillsell.dto_and_mapper.NewProductDto;
import com.mayank.skillsell.dto_and_mapper.ProductDto;
import com.mayank.skillsell.entity.Product;
import com.mayank.skillsell.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Product APIs", description = "Used to add, delete, update and retrieve products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProductsDto();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.getProductDtoById(id);
    }

    @GetMapping("/popular")
    public List<ProductDto> getPopularProducts() {
        return productService.getPopularProductsDto();
    }

    @PostMapping
    @PreAuthorize("hasRole('SELLER')")
    public ProductDto createProduct(@RequestBody NewProductDto newProductDto) {
        return productService.createProduct(newProductDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public Product updateProduct(@PathVariable Long id, @RequestBody NewProductDto newProductDto) {
        return productService.updateProduct(id, newProductDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "Product deleted successfully!";
    }

    // Get products by category ID
    @GetMapping("/category/id/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<ProductDto> products = productService.findProductsDtoByCategoryId(categoryId);

        // Check if products are found for the category
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();  // HTTP 204 No Content
        }

        return ResponseEntity.ok(products);  // HTTP 200 OK
    }

    // Get products by category name
    @GetMapping("/category/name/{categoryName}")
    public ResponseEntity<List<ProductDto>> getProductsByCategoryName(@PathVariable String categoryName) {
        List<ProductDto> products = productService.findProductDtoByCategoryName(categoryName);

        // Check if products are found for the category
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();  // HTTP 204 No Content
        }

        return ResponseEntity.ok(products);  // HTTP 200 OK
    }
}