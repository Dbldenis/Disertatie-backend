package com.example.dizertatie.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Medic")
@Table(name = "MEDIC", schema = "public")
public class Medic {

    // nume, prenume, specializare, telefon, cod parafa
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NUME")
    private String nume;

    @Column(name = "PRENUME_NUME")
    private String prenume;

    @Column(name = "SPECIALIZARE")
    private String specializare;

    @Column(name = "TELEFON")
    private String telefon;

    @Column(name = "COD_PARAFA")
    private Integer codParafa;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "medic")
    private List<Pacient> listaPacienti = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "medic")
    private Set<FisaPacientului> listaFisaPacienti = new HashSet<>();


    public void addPacient(Pacient pacient) {
        listaPacienti.add(pacient);
        pacient.setMedic(this);
    }

    public void addFisa(FisaPacientului fisaPacientului) {
        listaFisaPacienti.add(fisaPacientului);
        fisaPacientului.setMedic(this);
    }



}
