package com.mayank.skillsell.repository;

import com.mayank.skillsell.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    // Custom query method to check if an email exists
    boolean existsByEmail(String email);
    // Custom query method to check if a username exists
    boolean existsByUsername(String username);
    Optional<User> findByVerificationToken(String token);
}
