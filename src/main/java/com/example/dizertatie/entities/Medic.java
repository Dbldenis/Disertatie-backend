package com.example.dizertatie.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "medic")
    private List<FisaPacientului> listaFisaPacienti = new ArrayList<>();


    public void addPacient(Pacient pacient) {
        listaPacienti.add(pacient);
        pacient.setMedic(this);
    }

}
