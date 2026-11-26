package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientServiceRepository extends JpaRepository<ClientService, Long> {
    @EntityGraph(attributePaths = {"client", "serviceType"})
    Page<ClientService> findByClientId(Long clientId, Pageable pageable);

    @EntityGraph(attributePaths = {"client", "serviceType"})
    Page<ClientService> findByClientIdAndActiveTrue(Long clientId, Pageable pageable);

    boolean existsByClientIdAndServiceTypeId(Long clientId, Long serviceTypeId);
}
