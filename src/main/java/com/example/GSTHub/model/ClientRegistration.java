package com.example.GSTHub.model;

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client_registrations")
@Getter
@Setter
@NoArgsConstructor
public class ClientRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "gstin", nullable = false, unique = true, length = 15)
    private String gstin;

    @Column(name = "state_code", nullable = false, length = 2)
    private String stateCode;

    @Column(name = "registration_type", nullable = false, length = 50)
    private String registrationType;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "registration_status", nullable = false, length = 30)
    private String registrationStatus;

    @OneToMany(mappedBy = "registration", fetch = FetchType.LAZY)
    private List<ComplianceObligation> obligations = new ArrayList<>();

    @OneToMany(mappedBy = "registration", fetch = FetchType.LAZY)
    private List<Document> documents = new ArrayList<>();

    @OneToMany(mappedBy = "registration", fetch = FetchType.LAZY)
    private List<ReconciliationRun> reconciliationRuns = new ArrayList<>();
}
