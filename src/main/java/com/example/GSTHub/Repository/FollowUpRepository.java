package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.FollowUp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface FollowUpRepository extends JpaRepository<FollowUp, Long> {
    @EntityGraph(attributePaths = {"client", "obligation", "assignedTo"})
    Page<FollowUp> findByAssignedToId(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"client", "obligation", "assignedTo"})
    Page<FollowUp> findByFollowUpAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    @EntityGraph(attributePaths = {"client", "obligation", "assignedTo"})
    Page<FollowUp> findByStatus(String status, Pageable pageable);

    @EntityGraph(attributePaths = {"client", "obligation", "assignedTo"})
    Page<FollowUp> findByNextFollowUpAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
