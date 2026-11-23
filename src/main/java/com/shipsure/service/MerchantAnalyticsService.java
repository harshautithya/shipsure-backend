
package com.shipsure.service;

import com.shipsure.entity.OrderOutcome;
import com.shipsure.entity.Merchant;
import com.shipsure.repository.OrderRepository;
import com.shipsure.repository.OrderOutcomeRepository;
import com.shipsure.repository.MerchantRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MerchantAnalyticsService {

    private final OrderRepository orderRepository;
    private final OrderOutcomeRepository outcomeRepository;
    private final MerchantRepository merchantRepository;

    public MerchantAnalyticsService(
            OrderRepository orderRepository,
            OrderOutcomeRepository outcomeRepository,
            MerchantRepository merchantRepository) {

        this.orderRepository = orderRepository;
        this.outcomeRepository = outcomeRepository;
        this.merchantRepository = merchantRepository;
    }

    public Map<String, Object> getAnalytics(String email) {

        Merchant merchant = merchantRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("Merchant not found"));

        Long merchantId = merchant.getId();

        long totalOrders =
                orderRepository.findByMerchant_Id(merchantId).size();

        List<OrderOutcome> outcomes =
                outcomeRepository.findByOrder_Merchant_Id(merchantId);

        long deliveredOrders = outcomes.stream()
                .filter(o -> "DELIVERED".equalsIgnoreCase(o.getOutcome()))
                .count();

        long rtoOrders = outcomes.stream()
                .filter(o -> "RTO".equalsIgnoreCase(o.getOutcome()))
                .count();

        double rtoRate = totalOrders == 0
                ? 0.0
                : Math.round((rtoOrders * 10000.0 / totalOrders)) / 100.0;

        return Map.of(
                "totalOrders", totalOrders,
                "deliveredOrders", deliveredOrders,
                "rtoOrders", rtoOrders,
                "rtoRate", rtoRate
        );
    }
}
