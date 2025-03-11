package com.example.dizertatie.dto;

import com.example.dizertatie.entities.Medic;

import java.util.List;

public class ClinicaDto {

    private Long id;
    private String nume;
    private String adresa;
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
