package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.InvoiceItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
    @EntityGraph(attributePaths = "invoice")
    Page<InvoiceItem> findByInvoiceId(Long invoiceId, Pageable pageable);
}
