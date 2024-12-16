package com.mayank.skillsell.service;

import com.mayank.skillsell.entity.CartItem;
import com.mayank.skillsell.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    public CartItem getCartItemById(Long id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart item not found with id: " + id));
    }

    public List<CartItem> getCartItemsByCartId(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }

    public CartItem createCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}

