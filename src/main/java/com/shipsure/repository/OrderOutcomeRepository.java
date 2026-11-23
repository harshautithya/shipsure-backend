package com.shipsure.repository;

import com.shipsure.entity.OrderOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderOutcomeRepository
        extends JpaRepository<OrderOutcome, Long> {

    List<OrderOutcome> findByOrder_Customer_Id(Long customerId);

    List<OrderOutcome> findByOrder_Merchant_Id(Long merchantId);

}