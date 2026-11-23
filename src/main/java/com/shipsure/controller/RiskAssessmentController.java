package com.shipsure.controller;

import com.shipsure.dto.RiskAssessmentResponse;
import com.shipsure.service.RiskAssessmentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risk")
public class RiskAssessmentController {
    private final RiskAssessmentService riskAssessmentService;

    public RiskAssessmentController(RiskAssessmentService riskAssessmentService) {
        this.riskAssessmentService = riskAssessmentService;
    }
    @GetMapping("/customer/{id}")
    public RiskAssessmentResponse getCustomerRiskAssessment(
            @PathVariable Long id){
        int score=riskAssessmentService.calculateRiskScore(id);
        String level=riskAssessmentService.calculateRiskLevel(id);
        String recommendation=riskAssessmentService.getRecommendation(id);
        return new RiskAssessmentResponse(
                id,
                score,
                level,
                recommendation
        );
    }
    @GetMapping("/checkout")
    public RiskAssessmentResponse assessCheckout(
            @RequestParam String phone) {

        return riskAssessmentService.assessCustomerByPhone(phone);
    }
}
