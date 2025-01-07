package com.mayank.skillsell.dto_and_mapper;

import com.mayank.skillsell.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemMapper {
    @Autowired
    ProductMapper productMapper;
    public CartItemDto toCartItemDto(CartItem cartItem) {
        return new CartItemDto(
                cartItem.getQuantity(),
                productMapper.toProductDto(cartItem.getProduct())
        );
    }
}
