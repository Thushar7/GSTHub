package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @EntityGraph(attributePaths = {"client", "recordedBy"})
    Page<Payment> findByClientId(Long clientId, Pageable pageable);
}
