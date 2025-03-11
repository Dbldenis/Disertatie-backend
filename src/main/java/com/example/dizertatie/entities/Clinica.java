package com.example.dizertatie.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "Clinica")
@Table(name = "CLINICA")
public class Clinica {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NUME")
    private String nume;

    @Column(name = "ADRESA")
    private String adresa;

    // Lista medici

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "clinica")
    private List<Medic> listaMedici;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public List<Medic> getListaMedici() {
        return listaMedici;
    }

    public void setListaMedici(List<Medic> listaMedici) {
        this.listaMedici = listaMedici;
    }
}
