package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.DocumentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRequestRepository extends JpaRepository<DocumentRequest, Long> {
    Page<DocumentRequest> findByClientId(Long clientId, Pageable pageable);
    Page<DocumentRequest> findByRequestedById(Long userId, Pageable pageable);
    Page<DocumentRequest> findByStatus(String status, Pageable pageable);
}
