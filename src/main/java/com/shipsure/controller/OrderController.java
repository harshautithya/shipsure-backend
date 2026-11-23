package com.shipsure.controller;
import com.shipsure.entity.Order;

import com.shipsure.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("/api/orders")
    public Order createOrder(@Valid @RequestBody Order order){
        return orderService.createOrder(order);
    }
    @GetMapping("/api/orders/{id}")
    public Order getOrderById(@PathVariable Long id){
        return orderService.getOrderById(id);
    }
    @GetMapping("/api/orders")
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }
    @DeleteMapping("/api/orders/{id}")
    public void deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
    }
}
