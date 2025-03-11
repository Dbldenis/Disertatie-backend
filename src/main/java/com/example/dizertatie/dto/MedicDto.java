package com.example.dizertatie.dto;

import com.example.dizertatie.entities.Consultati;
import com.example.dizertatie.entities.Pacient;

import java.util.List;

public class MedicDto {

    // nume, prem, specializare, telefon, codparaf, listapacienti, consultati,
    private Long id;
    private String nume;
    private String prenume;
    private String specializare;
    private String telefon;
    private Integer codParafa;
    private List<Pacient> listaPacienti;
    private List<Consultati> consultati;

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

    public Integer getCodParafa() {
        return codParafa;
    }

    public void setCodParafa(Integer codParafa) {
        this.codParafa = codParafa;
    }

    public List<Pacient> getListaPacienti() {
        return this.listaPacienti;
    }

    public void setListaPacienti(List<Pacient> listaPacienti) {
        this.listaPacienti = listaPacienti;
    }

    public List<Consultati> getConsultati() {
        return consultati;
    }

    public void setConsultati(List<Consultati> consultati) {
        this.consultati = consultati;
    }
}
