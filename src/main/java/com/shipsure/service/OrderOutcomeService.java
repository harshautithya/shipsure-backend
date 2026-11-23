package com.shipsure.service;

import com.shipsure.entity.OrderOutcome;
import com.shipsure.repository.OrderOutcomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderOutcomeService {
    private final OrderOutcomeRepository orderOutcomeRepository;

    public OrderOutcomeService(OrderOutcomeRepository orderOutcomeRepository) {
        this.orderOutcomeRepository = orderOutcomeRepository;
    }
    public OrderOutcome createOrderOutcome(OrderOutcome orderOutcome){
        return orderOutcomeRepository.save(orderOutcome);
    }
    public List<OrderOutcome> getAllOrderOutcomes(){

        return orderOutcomeRepository.findAll();
    }
    public OrderOutcome getOrderOutcomeById(Long id){
        return orderOutcomeRepository.findById(id).orElseThrow();
    }
    public List<OrderOutcome> getCustomerOutcomes(Long id){
        return orderOutcomeRepository.findByOrder_Customer_Id(id);
    }
}
