package com.example.dizertatie.entities;

import jakarta.persistence.*;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "Clinica")
@Table(name = "CLINICA", schema = "public")
public class Clinica {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NUME")
    private String nume;

    @Column(name = "ADRESA")
    private String adresa;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Medic> listaMedici;

}
