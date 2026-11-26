package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @EntityGraph(attributePaths = {"client", "obligation", "assignedTo", "createdBy"})
    Page<Task> findByAssignedToId(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"client", "obligation", "assignedTo", "createdBy"})
    Page<Task> findByClientId(Long clientId, Pageable pageable);

    @EntityGraph(attributePaths = {"client", "obligation", "assignedTo", "createdBy"})
    Page<Task> findByStatus(String status, Pageable pageable);

    @EntityGraph(attributePaths = {"client", "obligation", "assignedTo", "createdBy"})
    Page<Task> findByStatusAndPriority(String status, String priority, Pageable pageable);

    @EntityGraph(attributePaths = {"client", "obligation", "assignedTo", "createdBy"})
    @Query("""
            select t from Task t
            where t.dueAt < :now
              and t.completedAt is null
              and t.status not in ('COMPLETED', 'CANCELLED')
            """)
    Page<Task> findOverdue(@Param("now") LocalDateTime now, Pageable pageable);
}
