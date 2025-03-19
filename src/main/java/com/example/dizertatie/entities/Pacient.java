package com.example.dizertatie.entities;

import jakarta.persistence.*;

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
