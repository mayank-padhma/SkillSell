package com.mayank.skillsell.config;

import com.mayank.skillsell.entity.User;
import com.mayank.skillsell.enums.UserRole;
import com.mayank.skillsell.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

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
}