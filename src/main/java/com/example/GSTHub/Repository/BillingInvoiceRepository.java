package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.BillingInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BillingInvoiceRepository extends JpaRepository<BillingInvoice, Long> {
    @EntityGraph(attributePaths = {"client", "createdBy"})
    Page<BillingInvoice> findByClientId(Long clientId, Pageable pageable);

    @EntityGraph(attributePaths = {"client", "createdBy"})
    Page<BillingInvoice> findByStatus(String status, Pageable pageable);

    @EntityGraph(attributePaths = {"client", "createdBy"})
    @Query("""
            select i from BillingInvoice i
            where i.dueDate < :today
              and i.status not in ('PAID', 'CANCELLED', 'VOID')
            """)
    Page<BillingInvoice> findOverdue(@Param("today") LocalDate today, Pageable pageable);
}
