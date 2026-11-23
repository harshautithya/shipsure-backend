package com.shipsure.controller;

import com.shipsure.entity.Customer;
import com.shipsure.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @PostMapping("/api/customers")
    public Customer addCustomer(@Valid @RequestBody Customer customer){

        return customerService.addCustomer(customer);
    }
    @GetMapping("/api/customers")
    public List<Customer> getCustomers(){
        return customerService.showCustomers();
    }
    @GetMapping("/api/customers/{id}")
    public Customer getCustomerById(@PathVariable Long id){
        return customerService.showCustomerById(id);
    }
    @PutMapping("/api/customers/{id}")
    public Customer updateCustomer(@RequestBody Customer customer,@PathVariable Long id){
        return customerService.updateCustomer(id,customer);
    }
    @DeleteMapping("/api/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
    }
}
