package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.ReconciliationItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReconciliationItemRepository extends JpaRepository<ReconciliationItem, Long> {
    @EntityGraph(attributePaths = "reconciliationRun")
    Page<ReconciliationItem> findByReconciliationRunIdAndMatchStatus(
            Long reconciliationRunId, String matchStatus, Pageable pageable);
}
