package com.example.GSTHub.Model;

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

@Entity
@Table(name = "client_contacts")
@Getter
@Setter
@NoArgsConstructor
public class ClientContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "phone", length = 30)
    private String phone;

    @Column(name = "email", length = 254)
    private String email;

    @Column(name = "designation", length = 100)
    private String designation;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;
}
