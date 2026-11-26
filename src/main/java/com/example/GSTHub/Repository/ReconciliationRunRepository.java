package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.ReconciliationRun;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReconciliationRunRepository extends JpaRepository<ReconciliationRun, Long> {
    @EntityGraph(attributePaths = {"client", "registration", "createdBy", "purchaseImport", "gstr2bImport"})
    Page<ReconciliationRun> findByClientIdAndRegistrationId(Long clientId, Long registrationId, Pageable pageable);
}
