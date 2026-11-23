package com.shipsure.controller;

import com.shipsure.entity.OrderOutcome;
import com.shipsure.service.OrderOutcomeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderOutcomeController {
    private final OrderOutcomeService orderOutcomeService;

    public OrderOutcomeController(OrderOutcomeService orderOutcomeService) {
        this.orderOutcomeService = orderOutcomeService;
    }
    @PostMapping("/api/order-outcome")
    public OrderOutcome createOrderOutcome(@RequestBody OrderOutcome orderOutcome){
        return orderOutcomeService.createOrderOutcome(orderOutcome);
    }
    @GetMapping("/api/order-outcome")
    public List<OrderOutcome> getAllOrderOutcomes(){
        return orderOutcomeService.getAllOrderOutcomes();
    }
    @GetMapping("/api/order-outcome/{id}")
    public OrderOutcome getOrderOutcomeById(@PathVariable Long id){
        return orderOutcomeService.getOrderOutcomeById(id);
    }
    @GetMapping("/api/order-outcome/customer/{customerId}")
    public List<OrderOutcome> getCustomerOutcomes(@PathVariable Long customerId){
        return orderOutcomeService.getCustomerOutcomes(customerId);
    }
}
