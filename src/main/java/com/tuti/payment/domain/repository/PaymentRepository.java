package com.tuti.payment.domain.repository;

import com.tuti.payment.domain.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payments, Long> {
    Optional<Payments> findByOrderUid(@Param("orderUid") String orderUid);
}
