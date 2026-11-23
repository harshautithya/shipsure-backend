
package com.shipsure.controller;

import com.shipsure.entity.Merchant;
import com.shipsure.entity.Order;
import com.shipsure.repository.MerchantRepository;
import com.shipsure.repository.OrderRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class RecentOrdersController {

    private final MerchantRepository merchantRepository;
    private final OrderRepository orderRepository;

    public RecentOrdersController(
            MerchantRepository merchantRepository,
            OrderRepository orderRepository) {

        this.merchantRepository = merchantRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/recent")
    public ResponseEntity<?> getRecentOrders(
            Authentication authentication) {

        // Step 1: Check authentication
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {

            return ResponseEntity.status(401).build();
        }

        // Step 2: Identify the logged-in merchant
        String email = authentication.getName();

        Merchant merchant = merchantRepository
                .findByEmail(email)
                .orElse(null);

        if (merchant == null) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "Merchant not found"));
        }

        // Step 3: Fetch only this merchant's orders
        List<Order> orders = orderRepository
                .findByMerchant_Id(merchant.getId());

        // Step 4: Sort newest first and limit to 10
        List<Map<String, Object>> recentOrders = orders.stream()
                .sorted(Comparator.comparing(
                        Order::getId,
                        Comparator.reverseOrder()
                ))
                .limit(10)
                .map(order -> Map.<String, Object>of(
                        "id", order.getId(),
                        "customerName", order.getCustomer().getName(),
                        "amount", order.getAmount(),
                        "paymentMethod",
                        order.getPaymentMethod() == null
                                ? "N/A"
                                : order.getPaymentMethod(),
                        "status",
                        order.getStatus() == null
                                ? "UNKNOWN"
                                : order.getStatus()
                ))
                .toList();

        // Step 5: Return JSON to React
        return ResponseEntity.ok(recentOrders);
    }
}
