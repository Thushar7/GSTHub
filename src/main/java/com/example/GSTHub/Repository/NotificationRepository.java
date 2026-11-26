package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @EntityGraph(attributePaths = "user")
    Page<Notification> findByUserIdAndReadAtIsNull(Long userId, Pageable pageable);
}
