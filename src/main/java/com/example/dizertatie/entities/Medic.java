package com.example.dizertatie.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Medic")
@Table(name = "MEDIC", schema = "public")
public class Medic {

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
    private Long codParafa;

    @Column(name = "PASSWORD")
    private String parola;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ANI_EXPERIENTA")
    private Integer aniExperienta;

    @Column(name = "VERIFICAT")
    private Boolean esteVerificat = false;

    @Column(name = "COD_VERIFICARE")
    private String codVerificare;

    @Column(name = "COD_VERIFICARE_GENERARE_TIMP")
    private LocalDateTime codVerificareGenerareTimp;

    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "medic")
    private List<Pacient> listaPacienti = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "clinica_id")
    @JsonBackReference
    private Clinica clinica;

    /*@JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "medic")
    private Set<FisaPacientului> listaFisaPacienti = new HashSet<>();*/


    public void addPacient(Pacient pacient) {
        listaPacienti.add(pacient);
        pacient.setMedic(this);
    }

    /*public void addFisa(FisaPacientului fisaPacientului) {
        listaFisaPacienti.add(fisaPacientului);
        fisaPacientului.setMedic(this);
    }*/



}
