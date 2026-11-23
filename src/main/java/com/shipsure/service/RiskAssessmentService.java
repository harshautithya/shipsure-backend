package com.shipsure.service;
import com.shipsure.dto.RiskAssessmentResponse;
import com.shipsure.entity.Customer;
import com.shipsure.entity.OrderOutcome;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskAssessmentService {
    private final OrderOutcomeService orderOutcomeService;
    private final CustomerService customerService;

    public RiskAssessmentService(
            OrderOutcomeService orderOutcomeService,
            CustomerService customerService) {
        this.orderOutcomeService = orderOutcomeService;
        this.customerService = customerService;
    }
    //RISK
    public int calculateRiskScore(Long id){
        List<OrderOutcome> outcomes=orderOutcomeService.getCustomerOutcomes(id);
        int totalPoints=0;
        if (outcomes.isEmpty()) {
            return 0;
        }
        for(OrderOutcome outcome:outcomes){
            if("RTO".equalsIgnoreCase(outcome.getOutcome())){
                totalPoints+=100;
            } else if ("DELIVERED".equalsIgnoreCase(outcome.getOutcome())) {
                totalPoints+=0;

            } else if ("CANCELLED".equalsIgnoreCase(outcome.getOutcome())) {
                totalPoints+=50;

            }
        }
        return Math.round((float)totalPoints/outcomes.size());
    }
    //LEVEL
    public String calculateRiskLevel(Long id){
        int score=calculateRiskScore(id);
        if(score<30){
            return "LOW";
        } else if (score<60) {
            return "MEDIUM";

        }
        else{
            return "HIGH";
        }

    }
    //RECCOMENDATION
    public String getRecommendation(Long id){
        String riskLevel=calculateRiskLevel(id);
        if(riskLevel.equalsIgnoreCase("LOW")){
            return "COD_ALLOWED";
        } else if (riskLevel.equalsIgnoreCase("MEDIUM")) {
            return "PARTIAL_PAYMENT";

        }
        else{
            return "PREPAID_ONLY";
        }

    }
    //CHECKOUT PAGE
    public RiskAssessmentResponse assessCustomerByPhone(String phone) {
        Customer customer = customerService.getCustomerByPhone(phone);

        Long customerId = customer.getId();
        int score = calculateRiskScore(customerId);
        String level = calculateRiskLevel(customerId);
        String recommendation = getRecommendation(customerId);

        return new RiskAssessmentResponse(
                customerId,
                score,
                level,
                recommendation
        );
    }

    }
