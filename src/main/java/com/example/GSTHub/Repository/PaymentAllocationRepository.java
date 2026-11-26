package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.PaymentAllocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentAllocationRepository extends JpaRepository<PaymentAllocation, Long> {
    @EntityGraph(attributePaths = {"payment", "invoice"})
    Page<PaymentAllocation> findByInvoiceId(Long invoiceId, Pageable pageable);
}
