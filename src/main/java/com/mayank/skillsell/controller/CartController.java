package com.mayank.skillsell.controller;

import com.mayank.skillsell.entity.Category;
import com.mayank.skillsell.entity.Cart;
import com.mayank.skillsell.service.CartService;
import com.mayank.skillsell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping("/{id}")
    public Cart getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);
    }

    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
        return cartService.createCart(cart);
    }

    @PutMapping("/{id}")
    public Cart updateCart(@PathVariable Long id, @RequestBody Cart cart) {
        return cartService.updateCart(id, cart);
    }

    @DeleteMapping("/{id}")
    public String deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return "Cart deleted successfully!";
    }
}
