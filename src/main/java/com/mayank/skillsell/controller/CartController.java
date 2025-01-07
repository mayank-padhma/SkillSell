package com.mayank.skillsell.controller;

import com.mayank.skillsell.dto_and_mapper.CartDto;
import com.mayank.skillsell.dto_and_mapper.CartItemDto;
import com.mayank.skillsell.dto_and_mapper.ProductDto;
import com.mayank.skillsell.entity.CartItem;
import com.mayank.skillsell.entity.Cart;
import com.mayank.skillsell.entity.User;
import com.mayank.skillsell.service.CartService;
import com.mayank.skillsell.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cart")
@Tag(name = "Cart APIs", description = "Handles the user's cart")
public class CartController {
    @Autowired
    private CartService cartService;

    // Add an item to the cart
    @PostMapping("/add-item")
    public ResponseEntity<String> addItemToCart(
            @RequestParam Long productId,
            @RequestParam Integer quantity,
            @AuthenticationPrincipal User user) {
        var cartId = user.getCart().getId();
        cartService.addItemToCart(cartId, productId, quantity);
        return ResponseEntity.ok("Item added to cart successfully");
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buyCart(@AuthenticationPrincipal User user) {
        cartService.buyCart(user.getId());
        return ResponseEntity.ok("Item added to cart successfully");
    }

    // Retrieve the cart by user ID
    @GetMapping("/user")
    public ResponseEntity<CartDto> getCartByUserId(
            @AuthenticationPrincipal User user
    ) {
        var userId = user.getId();
        CartDto cartDto = cartService.getCartDtoByUserId(userId);
        return ResponseEntity.ok(cartDto);
    }

    // Retrieve all items in the cart
    @GetMapping("/items")
    public ResponseEntity<List<CartItemDto>> getCartItems(
            @AuthenticationPrincipal User user
    ) {
        var cartId = user.getCart().getId();
        List<CartItemDto> cartItems = cartService.getCartItems(cartId);
        return ResponseEntity.ok(cartItems);
    }

    // Calculate the total price of the cart
    @GetMapping("/{cartId}/total-price")
    public ResponseEntity<Double> calculateTotalPrice(
            @AuthenticationPrincipal User user
    ) {
        var cartId = user.getCart().getId();
        Double totalPrice = cartService.calculateTotalPrice(cartId);
        return ResponseEntity.ok(totalPrice);
    }

    // Remove an item from the cart
    @DeleteMapping("/remove-item")
    public ResponseEntity<String> removeItemFromCart(
            @RequestParam Long productId,
            @AuthenticationPrincipal User user
    ) {
        var cartId = user.getCart().getId();
        cartService.removeItemFromCart(cartId, productId);
        return ResponseEntity.ok("Item removed from cart successfully");
    }
}
