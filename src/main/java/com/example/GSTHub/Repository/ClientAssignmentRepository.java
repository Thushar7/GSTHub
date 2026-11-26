package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.ClientAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientAssignmentRepository extends JpaRepository<ClientAssignment, Long> {
    @EntityGraph(attributePaths = {"client", "user", "assignedBy"})
    Page<ClientAssignment> findByClientIdAndActiveTrue(Long clientId, Pageable pageable);

    @EntityGraph(attributePaths = {"client", "user", "assignedBy"})
    Page<ClientAssignment> findByUserIdAndActiveTrue(Long userId, Pageable pageable);
}
