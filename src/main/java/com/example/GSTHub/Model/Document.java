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
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id")
    private ClientRegistration registration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "obligation_id")
    private ComplianceObligation obligation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uploaded_by_user_id", nullable = false)
    private User uploadedBy;

    @Column(name = "document_type", nullable = false, length = 50)
    private String documentType;

    @Column(name = "original_filename", nullable = false, length = 255)
    private String originalFilename;

    @Column(name = "storage_key", nullable = false, length = 1024)
    private String storageKey;

    @Column(name = "mime_type", length = 150)
    private String mimeType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "checksum_sha256", length = 64)
    private String checksumSha256;

    @Column(name = "tax_period", length = 30)
    private String taxPeriod;

    @Column(name = "verification_status", nullable = false, length = 30)
    private String verificationStatus;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "fulfilledDocument", fetch = FetchType.LAZY)
    private List<DocumentRequest> fulfilledRequests = new ArrayList<>();
}
