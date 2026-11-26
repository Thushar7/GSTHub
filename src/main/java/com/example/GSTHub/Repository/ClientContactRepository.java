package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.Client;
import com.example.GSTHub.Model.ClientContact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientContactRepository extends JpaRepository<ClientContact, Long> {
    Page<ClientContact> findByClient(Client client, Pageable pageable);
    List<ClientContact> findByClientIdAndIsPrimaryTrue(Long clientId);
}
