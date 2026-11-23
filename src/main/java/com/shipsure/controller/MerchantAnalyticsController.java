
package com.shipsure.controller;

import com.shipsure.service.MerchantAnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class MerchantAnalyticsController {

    private final MerchantAnalyticsService analyticsService;

    public MerchantAnalyticsController(
            MerchantAnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/merchant")
    public ResponseEntity<Map<String, Object>> getMerchantAnalytics(
            Authentication authentication) {

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {
            return ResponseEntity.status(401).build();
        }

        String email = authentication.getName();

        Map<String, Object> analytics =
                analyticsService.getAnalytics(email);

        return ResponseEntity.ok(analytics);
    }
}
