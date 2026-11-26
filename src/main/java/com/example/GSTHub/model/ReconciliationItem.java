package com.example.GSTHub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reconciliation_items")
@Getter
@Setter
@NoArgsConstructor
public class ReconciliationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reconciliation_run_id", nullable = false)
    private ReconciliationRun reconciliationRun;

    @Column(name = "invoice_number", length = 100)
    private String invoiceNumber;

    @Column(name = "supplier_gstin", length = 15)
    private String supplierGstin;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "purchase_taxable_value", precision = 19, scale = 4)
    private BigDecimal purchaseTaxableValue;

    @Column(name = "portal_taxable_value", precision = 19, scale = 4)
    private BigDecimal portalTaxableValue;

    @Column(name = "purchase_tax_amount", precision = 19, scale = 4)
    private BigDecimal purchaseTaxAmount;

    @Column(name = "portal_tax_amount", precision = 19, scale = 4)
    private BigDecimal portalTaxAmount;

    @Column(name = "match_status", nullable = false, length = 30)
    private String matchStatus;

    @Column(name = "remarks", columnDefinition = "text")
    private String remarks;
}
