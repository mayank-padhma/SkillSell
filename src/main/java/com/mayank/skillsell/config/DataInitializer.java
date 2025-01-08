package com.mayank.skillsell.config;

import com.mayank.skillsell.entity.Category;
import com.mayank.skillsell.entity.Product;
import com.mayank.skillsell.entity.User;
import com.mayank.skillsell.enums.UserRole;
import com.mayank.skillsell.repository.CategoryRepository;
import com.mayank.skillsell.repository.ProductRepository;
import com.mayank.skillsell.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Set;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminUsername = "admin";
            String adminPassword = "admin123";

            if (userRepository.findByUsername(adminUsername).isEmpty()) {
                User admin = new User();
                admin.setUsername(adminUsername);
                admin.setEmail("admin@skillsell.com");
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRoles(Set.of(UserRole.ADMIN.toString()));
                userRepository.save(admin);
                System.out.println("Admin user created: " + adminUsername);
            } else {
                System.out.println("Admin user already exists.");
            }
        };
    }

    @Bean
    public CommandLineRunner loadData(CategoryRepository categoryRepository, ProductRepository productRepository, UserRepository userRepository) {
        return args -> {
            // Create Seller User
//            User seller = new User();
//            seller.setUsername("seller1");
//            seller.setEmail("seller1@example.com");
//            seller.setPhoneNo("8383337373");
//            seller.setRoles(Set.of(UserRole.SELLER.toString()));
//            User savedSeller = userRepository.save(seller);
//
//            // Create Categories
//            Category electronics = new Category();
//            electronics.setName("Electronics");
//            electronics.setDescription("Electronic items are listed here. ");
//            Category fashion = new Category();
//            fashion.setName("Fashion");
//            fashion.setDescription("Fashion items are listed here. ");
//            categoryRepository.saveAll(Arrays.asList(electronics, fashion));
//
//            // Create Products
//            Product laptop = new Product();
//            laptop.setName("Laptop");
//            laptop.setDescription("High-performance laptop");
//            laptop.setPrice(1000.0);
//            laptop.setStock(10);
//            laptop.setSeller(savedSeller);
//            laptop.setCategory(electronics);
//
//            Product tShirt = new Product();
//            tShirt.setName("T-Shirt");
//            tShirt.setDescription("Comfortable cotton t-shirt");
//            tShirt.setPrice(20.0);
//            tShirt.setStock(50);
//            tShirt.setSeller(savedSeller);
//            tShirt.setCategory(fashion);
//
//            productRepository.saveAll(Arrays.asList(laptop, tShirt));
        };
    }
}