
package com.shipsure.config;

import com.shipsure.entity.Customer;
import com.shipsure.entity.Merchant;
import com.shipsure.entity.Order;
import com.shipsure.entity.OrderOutcome;

import com.shipsure.repository.CustomerRepository;
import com.shipsure.repository.MerchantRepository;
import com.shipsure.repository.OrderRepository;
import com.shipsure.repository.OrderOutcomeRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
@Profile("demo")
public class SampleDataLoader implements CommandLineRunner {

    private final MerchantRepository merchantRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final OrderOutcomeRepository outcomeRepository;

    public SampleDataLoader(
            MerchantRepository merchantRepository,
            CustomerRepository customerRepository,
            OrderRepository orderRepository,
            OrderOutcomeRepository outcomeRepository) {

        this.merchantRepository = merchantRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.outcomeRepository = outcomeRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        String[] names = {
                "Aarav Sharma", "Priya Verma", "Rohan Gupta",
                "Ananya Singh", "Kabir Mehta", "Sneha Kapoor",
                "Aditya Jain", "Isha Malhotra", "Rahul Yadav",
                "Neha Bansal", "Karan Sethi", "Pooja Mishra",
                "Vivek Kumar", "Simran Kaur", "Arjun Patel"
        };

        String[] outcomes = {
                "DELIVERED", "DELIVERED", "RTO",
                "DELIVERED", "CANCELLED", "DELIVERED",
                "DELIVERED", "RTO", "DELIVERED",
                "DELIVERED", "CANCELLED", "DELIVERED",
                "RTO", "DELIVERED", "DELIVERED"
        };

        List<Merchant> merchants = merchantRepository.findAll();

        for (Merchant merchant : merchants) {

            int existingOrders =
                    orderRepository.findByMerchant_Id(
                            merchant.getId()).size();

            if (existingOrders >= 15) {
                System.out.println(
                        "Skipping merchant " + merchant.getId()
                                + ": already has 15 or more orders.");
                continue;
            }

            for (int i = existingOrders; i < 15; i++) {

                String phone = String.format(
                        "900%07d",
                        merchant.getId() * 1000 + i
                );

                final int index = i;

                Customer customer = customerRepository
                        .findByPhone(phone)
                        .orElseGet(() -> {
                            Customer newCustomer = new Customer();
                            newCustomer.setName(names[index % names.length]);
                            newCustomer.setPhone(phone);
                            return customerRepository.save(newCustomer);
                        });

                Order order = new Order();
                order.setCustomer(customer);
                order.setMerchant(merchant);
                order.setAmount(
                        BigDecimal.valueOf(499 + i * 175)
                );
                order.setPaymentMethod(
                        i % 3 == 0 ? "PREPAID" : "COD"
                );

                String outcome = outcomes[i];

                order.setStatus(outcome);
                Order savedOrder = orderRepository.save(order);

                OrderOutcome orderOutcome = new OrderOutcome();
                orderOutcome.setOrder(savedOrder);
                orderOutcome.setOutcome(outcome);

                outcomeRepository.save(orderOutcome);
            }

            System.out.println(
                    "Sample orders added for merchant: "
                            + merchant.getId()
            );
        }

        System.out.println("ShipSure demo data loading completed.");
    }
}
