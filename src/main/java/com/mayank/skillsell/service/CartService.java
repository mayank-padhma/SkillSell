package com.mayank.skillsell.service;

import com.mayank.skillsell.entity.*;
import com.mayank.skillsell.entity.Cart;
import com.mayank.skillsell.repository.*;
import com.mayank.skillsell.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        boolean alreadyInCart = cart.getCartItems().stream()
                .anyMatch(cartItem -> cartItem.getProduct().getId().equals(productId));

        if (alreadyInCart) {
            throw new RuntimeException("Item already in cart");
        }

        // Create a new CartItem
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        // Associate the CartItem with the Cart
        cartItem.setCart(cart);

        // Add the CartItem to the Cart's list of items
        cart.getCartItems().add(cartItem);

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
        return cart.getCartItems();
    }

    // Calculate the total price of the cart
    public Double calculateTotalPrice(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        return cart.getCartItems().stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrice()) // Assuming price is Integer
                .sum();
    }

    // Removes an item from the cart
    @Transactional
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem cartItemToRemove = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        // Remove the item from the cart
        cart.getCartItems().remove(cartItemToRemove);

        // Delete the cart item from the repository
        cartItemRepository.delete(cartItemToRemove);

        // Save the updated cart
        cartRepository.save(cart);
    }
}