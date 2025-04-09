package com.example.dizertatie.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Pacient")
@Table(name = "PACIENT", schema = "public")
public class Pacient {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nume, prenume, email, telefon, cnp, adresa, asigurare, medic
    @Column(name = "NUME")
    private String nume;

    @Column(name = "PRENUME_NUME")
    private String prenume;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "TELEFON")
    private String telefon;

    @Column(name = "CNP") // varsta, data_nasterii, gen
    private String cnp;

    @Column(name = "ADRESA")
    private String adresa;

    @Column(name="ASIGURARE")
    private Boolean asigurare;

    @ManyToOne()
    @JoinColumn(name = "medic_id")
    @JsonBackReference
    private Medic medic;

    @OneToOne(mappedBy = "pacient", cascade = CascadeType.ALL)
    private FisaPacientului fisaPacientului;

    @OneToOne(mappedBy = "pacient", cascade = CascadeType.ALL)
    private Programare programare;

}
