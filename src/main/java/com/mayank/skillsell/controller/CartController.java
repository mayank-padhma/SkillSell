package com.mayank.skillsell.controller;

import com.mayank.skillsell.entity.CartItem;
import com.mayank.skillsell.entity.Category;
import com.mayank.skillsell.entity.Cart;
import com.mayank.skillsell.service.CartService;
import com.mayank.skillsell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    // Add an item to the cart
    @PostMapping("/{cartId}/add-item")
    public ResponseEntity<String> addItemToCart(
            @PathVariable Long cartId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        cartService.addItemToCart(cartId, productId, quantity);
        return ResponseEntity.ok("Item added to cart successfully");
    }

    // Retrieve the cart by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    // Retrieve all items in the cart
    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long cartId) {
        List<CartItem> cartItems = cartService.getCartItems(cartId);
        return ResponseEntity.ok(cartItems);
    }

    // Calculate the total price of the cart
    @GetMapping("/{cartId}/total-price")
    public ResponseEntity<Double> calculateTotalPrice(@PathVariable Long cartId) {
        Double totalPrice = cartService.calculateTotalPrice(cartId);
        return ResponseEntity.ok(totalPrice);
    }

    // Remove an item from the cart
    @DeleteMapping("/{cartId}/remove-item")
    public ResponseEntity<String> removeItemFromCart(
            @PathVariable Long cartId,
            @RequestParam Long productId) {
        cartService.removeItemFromCart(cartId, productId);
        return ResponseEntity.ok("Item removed from cart successfully");
    }
}
