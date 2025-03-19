package com.example.dizertatie.entities;

import jakarta.persistence.*;
import java.util.List;


/*@Entity(name = "FisaPacientului")
@Table(name = "FISA_PACIENTULUI", schema = "public")
public class FisaPacientului {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relație One-to-One cu Pacient
    @OneToOne
    @JoinColumn(name = "pacient_id")
    private Pacient pacient;

    // Date medicale din document (secțiunea I)
    private Double greutate; // în kg
    private Double inaltime; // în cm
    *//*private LocalDate dataNasterii;
    private String sex; // M/F/OTHER*//*

    // Istoric medical (secțiunea II din document)
    private String alergii; // ex: "penicilină, nuci"
    private String reactiiAdverse; // descriere reacții raportate
    private String medicamenteSuspecte; // denumiri medicamente
    private String evolutieReactie; // ex: "Recuperat cu sechele"

    // Medicamente concomitente (secțiunea III.3)
    @ElementCollection
    @CollectionTable(name = "MEDICAMENTE_CONCOMITENTE", joinColumns = @JoinColumn(name = "fisa_id"))
    private List<String> medicamenteConcomitente;

    // Detalii raportor (secțiunea IV)
    private String numeRaportor;
    private String relatieCuPacientul; // ex: "Îngrijitor"

    // Relație cu Consultații (o fișă poate avea mai multe consultații)
    @OneToMany(mappedBy = "fisa", cascade = CascadeType.ALL)
    private List<Consultatie> consultatii;

    // Getters și Setters
}*/
