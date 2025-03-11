package com.example.dizertatie.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "Consultati")
@Table(name = "CONSULTATI",schema = "public")
public class Consultati {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATA")
    private LocalDateTime dataConsultatiei;

    @Column(name = "OBSERVATI")
    private String observati;

    @Column(name = "SIMPTOME")
    private String simptome;

    @Column(name = "TRATAMENT")
    private String tratament;

    @Column(name = "DIAGNOSTIC")
    private String diagnostic;

    @ManyToOne()
    @JoinColumn(name = "pacient_id")
    private Pacient pacient;

    @ManyToOne()
    @JoinColumn(name = "medic_id")
    private Medic medic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataConsultatiei() {
        return dataConsultatiei;
    }

    public void setDataConsultatiei(LocalDateTime dataConsultatiei) {
        this.dataConsultatiei = dataConsultatiei;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    public Medic getMedic() {
        return medic;
    }

    public void setMedic(Medic medic) {
        this.medic = medic;
    }

    public String getObservati() {
        return observati;
    }

    public void setObservati(String observati) {
        this.observati = observati;
    }

    public String getSimptome() {
        return simptome;
    }

    public void setSimptome(String simptome) {
        this.simptome = simptome;
    }

    public String getTratament() {
        return tratament;
    }

    public void setTratament(String tratament) {
        this.tratament = tratament;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }
}
