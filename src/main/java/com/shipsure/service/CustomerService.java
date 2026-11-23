package com.shipsure.service;

import com.shipsure.entity.Customer;
import com.shipsure.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;
    }
    public Customer addCustomer(Customer customer){

        return customerRepository.save(customer);
    }
    public List<Customer> showCustomers(){

        return customerRepository.findAll();
    }
    public Customer showCustomerById(Long id){
        return  customerRepository.findById(id).orElseThrow();
    }
    public Customer updateCustomer(Long id,Customer customer){
        Customer existingCustomer=customerRepository.findById(id).orElseThrow();
        existingCustomer.setName(customer.getName());
        existingCustomer.setPhone(customer.getPhone());
        return customerRepository.save(existingCustomer);

    }
    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }
    public Customer getCustomerByPhone(String phone) {
        return customerRepository.findByPhone(phone)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));
    }
}
