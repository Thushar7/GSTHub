package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
    Page<ServiceType> findByActiveTrue(Pageable pageable);
    Optional<ServiceType> findByCode(String code);
}
