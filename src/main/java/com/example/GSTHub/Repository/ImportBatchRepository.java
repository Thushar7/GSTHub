package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.ImportBatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportBatchRepository extends JpaRepository<ImportBatch, Long> {
    @EntityGraph(attributePaths = {"client", "uploadedBy"})
    Page<ImportBatch> findByClientIdAndImportType(Long clientId, String importType, Pageable pageable);
}
