package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.ClientRegistration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRegistrationRepository extends JpaRepository<ClientRegistration, Long> {
    @EntityGraph(attributePaths = "client")
    Page<ClientRegistration> findByClientId(Long clientId, Pageable pageable);

    Optional<ClientRegistration> findByGstin(String gstin);

    boolean existsByGstin(String gstin);
}
