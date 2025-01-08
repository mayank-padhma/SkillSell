package com.mayank.skillsell.controller;

import com.mayank.skillsell.dto_and_mapper.CartDto;
import com.mayank.skillsell.dto_and_mapper.CartItemDto;
import com.mayank.skillsell.dto_and_mapper.ProductDto;
import com.mayank.skillsell.entity.CartItem;
import com.mayank.skillsell.entity.Cart;
import com.mayank.skillsell.entity.User;
import com.mayank.skillsell.service.CartService;
import com.mayank.skillsell.service.ProductService;
import com.mayank.skillsell.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cart")
@Tag(name = "Cart APIs", description = "Handles the user's cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    // Add an item to the cart
    @PostMapping("/add-item")
    public ResponseEntity<String> addItemToCart(
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        var cartId = user.getCart().getId();
        cartService.addItemToCart(cartId, productId, quantity);
        return ResponseEntity.ok("Item added to cart successfully");
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buyCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        cartService.buyCart(user.getId());
        return ResponseEntity.ok("Items in cart bought successfully");
    }

    // Retrieve the cart by user ID
    @GetMapping("/user")
    public ResponseEntity<CartDto> getCartByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        var userId = user.getId();
        CartDto cartDto = cartService.getCartDtoByUserId(userId);
        return ResponseEntity.ok(cartDto);
    }

    // Retrieve all items in the cart
    @GetMapping("/items")
    public ResponseEntity<List<CartItemDto>> getCartItems() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        var cartId = user.getCart().getId();
        List<CartItemDto> cartItems = cartService.getCartItems(cartId);
        return ResponseEntity.ok(cartItems);
    }

    // Calculate the total price of the cart
    @GetMapping("/{cartId}/total-price")
    public ResponseEntity<Double> calculateTotalPrice() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        var cartId = user.getCart().getId();
        Double totalPrice = cartService.calculateTotalPrice(cartId);
        return ResponseEntity.ok(totalPrice);
    }

    // Remove an item from the cart
    @DeleteMapping("/remove-item")
    public ResponseEntity<String> removeItemFromCart(
            @RequestParam Long productId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        var cartId = user.getCart().getId();
        cartService.removeItemFromCart(cartId, productId);
        return ResponseEntity.ok("Item removed from cart successfully");
    }
}
