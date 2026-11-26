package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.ComplianceObligation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ComplianceObligationRepository extends JpaRepository<ComplianceObligation, Long> {
    @EntityGraph(attributePaths = {"client", "registration", "serviceType", "assignedTo"})
    Page<ComplianceObligation> findByClientId(Long clientId, Pageable pageable);

    @EntityGraph(attributePaths = {"client", "registration", "serviceType", "assignedTo"})
    Page<ComplianceObligation> findByAssignedToId(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"client", "registration", "serviceType", "assignedTo"})
    Page<ComplianceObligation> findByDueDateBetweenAndStatus(LocalDate start, LocalDate end, String status, Pageable pageable);

    @EntityGraph(attributePaths = {"client", "registration", "serviceType", "assignedTo"})
    Page<ComplianceObligation> findByFinancialYearAndPeriodStartAndPeriodEndAndStatus(
            String financialYear, LocalDate periodStart, LocalDate periodEnd, String status, Pageable pageable);

    @EntityGraph(attributePaths = {"client", "registration", "serviceType", "assignedTo"})
    Page<ComplianceObligation> findByFinancialYearAndStatus(String financialYear, String status, Pageable pageable);

    @EntityGraph(attributePaths = {"client", "registration", "serviceType", "assignedTo"})
    @Query("""
            select o from ComplianceObligation o
            where o.dueDate < :today
              and o.status not in ('COMPLETED', 'FILED', 'CANCELLED')
            """)
    Page<ComplianceObligation> findOverdue(@Param("today") LocalDate today, Pageable pageable);
}
