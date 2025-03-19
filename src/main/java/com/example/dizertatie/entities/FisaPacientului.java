package com.example.dizertatie.entities;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "FisaPacientului")
@Table(name = "FISA_PACIENTULUI", schema = "public")
public class FisaPacientului {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relație One-to-One cu Pacient
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    @JoinColumn(name = "pacient_id", referencedColumnName = "id")
    private Pacient pacient;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    @JoinColumn(name = "medic_id", referencedColumnName = "id")
    private Medic medic;

    // Date medicale din document (secțiunea I)
    private Double greutate; // în kg
    private Double inaltime; // în cm

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
    /*@OneToMany(mappedBy = "fisa", cascade = CascadeType.ALL)
    private List<Consultatie> consultatii;*/

}
