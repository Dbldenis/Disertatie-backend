package com.example.dizertatie.dto;

import com.example.dizertatie.entities.Medic;

public class PacientDto {

    // nume, prenume, email, telefon, cnp, adresa, asigurare, medic

    private Long id;
    private String nume;
    private String prenume;
    private String email;
    private String telefon;
    private String cnp;
    private String adresa;
    private Boolean asigurare;
    private Medic medic;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Boolean getAsigurare() {
        return asigurare;
    }

    public void setAsigurare(Boolean asigurare) {
        this.asigurare = asigurare;
    }

    public Medic getMedic() {
        return medic;
    }

    public void setMedic(Medic medic) {
        this.medic = medic;
    }

}
