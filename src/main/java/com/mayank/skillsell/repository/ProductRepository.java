package com.mayank.skillsell.repository;

import com.mayank.skillsell.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByCategoryName(String categoryName);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    @Query("SELECT p FROM Product p ORDER BY p.searchCount + p.viewCount + p.purchaseCount DESC")
    Page<Product> findPopularProducts(Pageable pageable);
    @Modifying
    @Query("UPDATE Product p SET p.viewCount = p.viewCount + 1 WHERE p.id = :id")
    void incrementViewCount(@Param("id") Long id);
    @Modifying
    @Query("UPDATE Product p SET p.searchCount = p.searchCount + 1 WHERE p.id = :id")
    void incrementSearchCount(@Param("id") Long id);
    @Modifying
    @Query("UPDATE Product p SET p.purchaseCount = p.purchaseCount + 1 WHERE p.id = :id")
    void incrementPurchaseCount(@Param("id") Long id);
    @Modifying
    @Query("UPDATE Product p SET p.stock = p.stock - :quantity WHERE p.id = :productId AND p.stock >= :quantity")
    int decrementStock(@Param("productId") Long productId, @Param("quantity") int quantity);

}