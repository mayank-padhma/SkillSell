package com.mayank.skillsell.controller;

import com.mayank.skillsell.dto_and_mapper.OrderDto;
import com.mayank.skillsell.entity.Order;
import com.mayank.skillsell.entity.User;
import com.mayank.skillsell.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order APIs", description = "Handles the orders for sellers and customers")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/buy")
    public List<OrderDto> getAllOrdersByBuyerId(@AuthenticationPrincipal User user
    ) {
        var userId = user.getId();
        return orderService.getOrdersDtoByBuyerId(userId);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderDto> getAllOrders(@AuthenticationPrincipal User user
    ) {
        return orderService.getOrdersDto();
    }

    @GetMapping("/sell")
    public List<OrderDto> getAllOrdersBySellerId(@AuthenticationPrincipal User user
    ) {
        var userId = user.getId();
        return orderService.getOrdersDtoBySellerId(userId);
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        return orderService.getOrderDtoById(id);
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