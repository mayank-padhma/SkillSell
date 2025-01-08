package com.mayank.skillsell.service;

import com.mayank.skillsell.dto_and_mapper.*;
import com.mayank.skillsell.entity.*;
import com.mayank.skillsell.entity.Cart;
import com.mayank.skillsell.repository.*;
import com.mayank.skillsell.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

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

    // Retrieves the cartDto by user ID
    public CartDto getCartDtoByUserId(Long userId) {
        return cartMapper.toCartDto(cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found for this user")));
    }

    // Retrieves the cart by user ID
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found for this user"));
    }

    // Retrieves all items in the cart
    public List<CartItemDto> getCartItems(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        return cart.getCartItems()
                .stream().map(cartItemMapper::toCartItemDto)
                .collect(Collectors.toList());
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

    @Transactional
    public void buyCart(Long buyerId) {
        Cart cart = cartRepository.findByUserId(buyerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Order order = new Order();
        order.setBuyer(userService.getUserById(buyerId));
        order.setOrderItems(new ArrayList<>());

        double totalPrice = 0.0;

        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();

            // Check stock and decrement atomically
            int updated = productRepository.decrementStock(product.getId(), cartItem.getQuantity());
            if (updated == 0) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // Increment purchase count
            productRepository.incrementPurchaseCount(product.getId());

            // Calculate price for current item
            double itemPrice = cartItem.getQuantity() * product.getPrice();
            totalPrice += itemPrice;

            // Create OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(itemPrice); // Set the price of the item
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }

        // Set total price of the order
        order.setTotalPrice(totalPrice);

        // Clear the cart and delete cart items
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();

        // Save the order
        orderService.createOrder(order);
    }
}