
package com.shipsure.dto;

public class RiskAssessmentResponse {

    private Long customerId;
    private int riskScore;
    private String riskLevel;
    private String recommendation;

    public RiskAssessmentResponse(
            Long customerId,
            int riskScore,
            String riskLevel,
            String recommendation) {
        this.customerId = customerId;
        this.riskScore = riskScore;
        this.riskLevel = riskLevel;
        this.recommendation = recommendation;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public String getRecommendation() {
        return recommendation;
    }
}
