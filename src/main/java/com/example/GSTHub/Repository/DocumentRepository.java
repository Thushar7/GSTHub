package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    @EntityGraph(attributePaths = {"client", "registration", "obligation", "uploadedBy"})
    Page<Document> findByClientId(Long clientId, Pageable pageable);

    @EntityGraph(attributePaths = {"client", "registration", "obligation", "uploadedBy"})
    Page<Document> findByObligationId(Long obligationId, Pageable pageable);

    @EntityGraph(attributePaths = {"client", "registration", "obligation", "uploadedBy"})
    Page<Document> findByVerificationStatus(String verificationStatus, Pageable pageable);
}
