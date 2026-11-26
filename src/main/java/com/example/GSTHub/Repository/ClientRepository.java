package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByClientCode(String clientCode);

    boolean existsByClientCode(String clientCode);

    Page<Client> findByStatus(String status, Pageable pageable);

    Page<Client> findByStatusIgnoreCase(String status, Pageable pageable);

    @Query("""
            select c from Client c
            where lower(c.businessName) like lower(concat('%', :term, '%'))
               or lower(c.tradeName) like lower(concat('%', :term, '%'))
               or c.phone like concat('%', :term, '%')
               or exists (
                   select r.id from ClientRegistration r
                   where r.client = c and r.gstin like concat('%', :term, '%')
               )
            """)
    Page<Client> search(@Param("term") String term, Pageable pageable);
}
