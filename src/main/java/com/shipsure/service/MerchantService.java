
package com.shipsure.service;

import com.shipsure.entity.Merchant;
import com.shipsure.repository.MerchantRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantService {

    private final MerchantRepository merchantRepository;
    private final PasswordEncoder passwordEncoder;

    public MerchantService(
            MerchantRepository merchantRepository,
            PasswordEncoder passwordEncoder) {
        this.merchantRepository = merchantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Existing CRUD method
    public Merchant createMerchant(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    // New registration method
    public Merchant registerMerchant(
            String name, String email, String password) {

        if (merchantRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "An account with this email already exists.");
        }

        Merchant merchant = new Merchant();
        merchant.setName(name);
        merchant.setEmail(email);
        merchant.setPassword(passwordEncoder.encode(password));

        return merchantRepository.save(merchant);
    }

    // Existing CRUD methods
    public List<Merchant> getAllMerchant() {
        return merchantRepository.findAll();
    }

    public Merchant getMerchantById(Long id) {
        return merchantRepository.findById(id).orElseThrow();
    }

    public Merchant updateMerchant(Long id, Merchant merchant) {
        Merchant existingMerchant =
                merchantRepository.findById(id).orElseThrow();

        existingMerchant.setName(merchant.getName());
        existingMerchant.setEmail(merchant.getEmail());

        return merchantRepository.save(existingMerchant);
    }

    public void deleteMerchant(Long id) {
        merchantRepository.deleteById(id);
    }
    public Merchant loginMerchant(String email, String rawPassword) {

        Merchant merchant = merchantRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid email or password"));

        boolean passwordMatches = passwordEncoder.matches(
                rawPassword,
                merchant.getPassword()
        );

        if (!passwordMatches) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return merchant;
    }
}
