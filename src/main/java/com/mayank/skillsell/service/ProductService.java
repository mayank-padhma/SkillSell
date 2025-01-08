package com.mayank.skillsell.service;

import com.mayank.skillsell.dto_and_mapper.NewProductDto;
import com.mayank.skillsell.dto_and_mapper.ProductDto;
import com.mayank.skillsell.dto_and_mapper.ProductMapper;
import com.mayank.skillsell.entity.Category;
import com.mayank.skillsell.entity.Product;
import com.mayank.skillsell.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private CategoryService categoryService;

    private static final String POPULAR_PRODUCTS_KEY = "popular_products";

    // Scheduled task to update popular products cache
    @Scheduled(fixedRate = 3600000) // Every hour
    public void cachePopularProducts() {
        Page<Product> popularProductsPage = productRepository.findPopularProducts(PageRequest.of(0, 10));

        // Extract the content from the Page
        List<Product> popularProducts = popularProductsPage.getContent();
        // Save popular products to Redis
        redisService.set(POPULAR_PRODUCTS_KEY, popularProducts, 3600L); // Cache for 1 hour
    }

    // Retrieve popular products
    public List<Product> getPopularProducts() {
        // Try Redis first
        List<Product> popularProducts = redisService.get(POPULAR_PRODUCTS_KEY, List.class);

        if (popularProducts == null || popularProducts.isEmpty()) {
            // Fallback to database if not in Redis
            popularProducts = productRepository.findPopularProducts(PageRequest.of(0, 10)).getContent();

            // Update Redis cache
            redisService.set(POPULAR_PRODUCTS_KEY, popularProducts, 3600L);
        }

        return popularProducts;
    }

    public List<ProductDto> getPopularProductsDto() {
        // Try Redis first
        List<Product> popularProducts = redisService.get(POPULAR_PRODUCTS_KEY, List.class);

        if (popularProducts == null || popularProducts.isEmpty()) {
            // Fallback to database if not in Redis
            popularProducts = productRepository.findPopularProducts(PageRequest.of(0, 10)).getContent();

            // Update Redis cache
            redisService.set(POPULAR_PRODUCTS_KEY, popularProducts, 3600L);
        }

        return popularProducts.stream().map(productMapper::toProductDto).collect(Collectors.toList());
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public List<ProductDto> getAllProductsDto() {
        return productRepository.findAll().stream().map(productMapper::toProductDto).collect(Collectors.toList());
    }

    public Product getProductById(Long id) {
        var product =  productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.incrementViewCount(product.getId());
        return product;
    }

    @Transactional
    public ProductDto getProductDtoById(Long id) {
        var product =  productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.incrementViewCount(product.getId());
        return productMapper.toProductDto(product);
    }

    public ProductDto createProduct(NewProductDto newProductDto) {
        Category category = categoryService.getCategoryById(newProductDto.categoryId());
        Product product = new Product();
        product.setName(newProductDto.name());
        product.setDescription(newProductDto.description());
        product.setPrice(newProductDto.price());
        product.setStock(newProductDto.stock());
        product.setImageUrl(newProductDto.imageUrl());
        product.setCategory(category);
        return productMapper.toProductDto(productRepository.save(product));
    }

    public Product updateProduct(Long id, NewProductDto productDto) {
        Product existingProduct = getProductById(id);
        Category category = categoryService.getCategoryById(productDto.categoryId());
        existingProduct.setName(productDto.name());
        existingProduct.setDescription(productDto.description());
        existingProduct.setPrice(productDto.price());
        existingProduct.setStock(productDto.stock());
        existingProduct.setImageUrl(productDto.imageUrl());
        existingProduct.setCategory(category);
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
    public List<ProductDto> findProductsDtoByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream().map(productMapper::toProductDto).collect(Collectors.toList());
    }

    public List<Product> findByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

    public List<ProductDto> findProductDtoByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName).stream().map(productMapper::toProductDto).collect(Collectors.toList());
    }

    public List<Product> searchProductsByName(String name) {
        var products = productRepository.findByNameContainingIgnoreCase(name);
        products.forEach(product -> productRepository.incrementSearchCount(product.getId()));
        return products;
    }

    @Transactional
    public List<ProductDto> searchProductsDtoByName(String name) {
        var products = productRepository.findByNameContainingIgnoreCase(name);
        products.forEach(product -> productRepository.incrementSearchCount(product.getId()));
        return products.stream().map(productMapper::toProductDto).collect(Collectors.toList());
    }

    public List<Product> filterProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
}
