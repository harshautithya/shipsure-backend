package com.shipsure.controller;

import com.shipsure.dto.MerchantLoginRequest;
import com.shipsure.dto.MerchantRegisterRequest;
import com.shipsure.entity.Merchant;
import com.shipsure.service.JwtService;
import com.shipsure.service.MerchantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final MerchantService merchantService;
    private final JwtService jwtService;

    public AuthController(
            MerchantService merchantService,
            JwtService jwtService) {
        this.merchantService = merchantService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerMerchant(
            @Valid @RequestBody MerchantRegisterRequest request) {

        Merchant merchant = merchantService.registerMerchant(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new MerchantResponse(
                        merchant.getId(),
                        merchant.getName(),
                        merchant.getEmail()
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginMerchant(
            @Valid @RequestBody MerchantLoginRequest request) {

        Merchant merchant = merchantService.loginMerchant(
                request.getEmail(),
                request.getPassword()
        );

        String token = jwtService.generateToken(merchant.getEmail());

        return ResponseEntity.ok(
                new LoginResponse(
                        merchant.getId(),
                        merchant.getName(),
                        merchant.getEmail(),
                        token
                )
        );
    }

    public record MerchantResponse(
            Long id,
            String name,
            String email
    ) {}

    public record LoginResponse(
            Long id,
            String name,
            String email,
            String token
    ) {}
}