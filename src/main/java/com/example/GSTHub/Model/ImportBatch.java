package com.example.GSTHub.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "import_batches")
@Getter
@Setter
@NoArgsConstructor
public class ImportBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uploaded_by_user_id", nullable = false)
    private User uploadedBy;

    @Column(name = "import_type", nullable = false, length = 50)
    private String importType;

    @Column(name = "filename", nullable = false, length = 255)
    private String filename;

    @Column(name = "status", nullable = false, length = 30)
    private String status;

    @Column(name = "total_rows", nullable = false)
    private Integer totalRows = 0;

    @Column(name = "successful_rows", nullable = false)
    private Integer successfulRows = 0;

    @Column(name = "failed_rows", nullable = false)
    private Integer failedRows = 0;

    @Column(name = "error_report_key", length = 1024)
    private String errorReportKey;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "purchaseImport", fetch = FetchType.LAZY)
    private List<ReconciliationRun> purchaseReconciliationRuns = new ArrayList<>();

    @OneToMany(mappedBy = "gstr2bImport", fetch = FetchType.LAZY)
    private List<ReconciliationRun> gstr2bReconciliationRuns = new ArrayList<>();
}
