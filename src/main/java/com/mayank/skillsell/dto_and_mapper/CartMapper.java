package com.mayank.skillsell.dto_and_mapper;

import com.mayank.skillsell.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CartMapper {
    @Autowired
    private CartItemMapper mapper;
    public CartDto toCartDto(Cart cart) {
        return new CartDto(
                cart.getCartItems().stream().map(mapper::toCartItemDto).collect(Collectors.toList())
        );
    }
}
