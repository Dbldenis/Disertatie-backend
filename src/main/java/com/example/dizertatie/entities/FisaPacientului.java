package com.example.dizertatie.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity(name = "FisaPacientului")
@Table(name = "FISA_PACIENTULUI", schema = "public")
public class FisaPacientului {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "pacient_id", referencedColumnName = "id")
    private Pacient pacient;

    // Date medicale din document (secțiunea I)
    private Double greutate;
    private Double inaltime;

    // Istoric medical (secțiunea II din document)
    private String alergii;
    private String reactiiAdverse;
    private String medicamenteSuspecte;
    private String evolutieReactie;

    // Medicamente concomitente (secțiunea III.3)
    /*@ElementCollection
    @CollectionTable(name = "MEDICAMENTE_CONCOMITENTE", joinColumns = @JoinColumn(name = "fisa_id"))
    private List<String> medicamenteConcomitente;*/

    // Detalii raportor (secțiunea IV)
    private String numeRaportor;
    private String relatieCuPacientul;

    // Relație cu Consultații (o fișă poate avea mai multe consultații)
    @OneToMany(mappedBy = "fisaPacientului", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Consultatie> listaConsultati;

}
