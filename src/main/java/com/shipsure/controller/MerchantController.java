package com.shipsure.controller;

import com.shipsure.entity.Merchant;
import com.shipsure.service.MerchantService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MerchantController {
 private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }
    @PostMapping("/api/merchants")
    public Merchant createMerchant(@Valid @RequestBody Merchant merchant){
        return merchantService.createMerchant(merchant);
    }
    @GetMapping("/api/merchants")
    public List<Merchant> getAllMerchant(){
        return merchantService.getAllMerchant();
    }
    @GetMapping("/api/merchants/{id}")
    public Merchant getMerchant(@PathVariable Long id){
        return merchantService.getMerchantById(id);
    }
    @PutMapping("/api/merchants/{id}")
    public Merchant updateMerchant(@PathVariable Long id,@RequestBody Merchant merchant){
        return merchantService.updateMerchant(id,merchant);
    }
    @DeleteMapping("/api/merchants/{id}")
    public void deleteMerchant(@PathVariable Long id){
        merchantService.deleteMerchant(id);
    }
}
