package com.example.dizertatie.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
    private List<Consultati> listaConsultati;

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

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getSpecializare() {
        return specializare;
    }

    public void setSpecializare(String specializare) {
        this.specializare = specializare;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public List<Pacient> getListaPacienti() {
        return listaPacienti;
    }

    public void setListaPacienti(List<Pacient> listaPacienti) {
        this.listaPacienti = listaPacienti;
    }

    public Integer getCodParafa() {
        return codParafa;
    }

    public void setCodParafa(Integer codParafa) {
        this.codParafa = codParafa;
    }

    public List<Consultati> getConsultati() {
        return listaConsultati;
    }

    public void setConsultati(List<Consultati> consultati) {
        this.listaConsultati = consultati;
    }
}
