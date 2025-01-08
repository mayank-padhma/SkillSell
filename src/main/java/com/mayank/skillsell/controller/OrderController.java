package com.mayank.skillsell.controller;

import com.mayank.skillsell.dto_and_mapper.OrderDto;
import com.mayank.skillsell.entity.Order;
import com.mayank.skillsell.entity.User;
import com.mayank.skillsell.exceptions.OrderNotFoundException;
import com.mayank.skillsell.service.OrderService;
import com.mayank.skillsell.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order APIs", description = "Handles the orders for sellers and customers")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @GetMapping("/buy")
    public List<OrderDto> getAllOrdersByBuyerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        var userId = user.getId();
        return orderService.getOrdersDtoByBuyerId(userId);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderDto> getAllOrders(
    ) {
        return orderService.getOrdersDto();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        try {
            OrderDto orderDto = orderService.getOrderDtoById(id);
            return ResponseEntity.ok(orderDto);
        } catch (OrderNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No order found with ID: " + id);
        }
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "Order deleted successfully!";
    }
}