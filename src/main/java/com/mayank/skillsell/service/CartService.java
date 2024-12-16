package com.mayank.skillsell.service;

import com.mayank.skillsell.entity.*;
import com.mayank.skillsell.entity.Cart;
import com.mayank.skillsell.repository.*;
import com.mayank.skillsell.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    // Adds an item to the cart
    @Transactional
    public void addItemToCart(Long cartId, Long productId, Integer quantity) {
        // Fetch the cart and product from the database
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if the cart already contains the product
        boolean alreadyInCart = cart.getItems().stream()
                .anyMatch(cartItem -> cartItem.getProduct().getId().equals(productId));

        if (alreadyInCart) {
            throw new RuntimeException("Item already in cart");
        }

        // Create a new CartItem
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(product.getPrice() * quantity);  // Price calculation

        // Associate the CartItem with the Cart
        cartItem.setCart(cart);

        // Add the CartItem to the Cart's list of items
        cart.addItem(cartItem);

        // Save the updated cart with the new item
        cartRepository.save(cart);
    }

    // Retrieves the cart by user ID
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found for this user"));
    }

    // Retrieves all items in the cart
    public List<CartItem> getCartItems(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        return cart.getItems();
    }

    // Calculate the total price of the cart
    public Integer calculateTotalPrice(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        return cart.getItems().stream()
                .mapToInt(cartItem -> cartItem.getPrice().intValue())
                .sum();
    }

    // Removes an item from the cart
    @Transactional
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem cartItemToRemove = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        // Remove the item from the cart
        cart.getItems().remove(cartItemToRemove);

        // Delete the cart item from the repository
        cartItemRepository.delete(cartItemToRemove);

        // Save the updated cart
        cartRepository.save(cart);
    }
}