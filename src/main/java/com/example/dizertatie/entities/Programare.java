package com.example.dizertatie.entities;


import jakarta.persistence.*;

@Entity(name="Programare")
@Table(name="PROGRAMARE",schema = "public")
public class Programare {

    @Id
    @Column
    private Long id;

    @Column(name = "TIP_CONSULTATIE")
    private Consultatie tipConsutlatie;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    private Pacient pacient;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    private Medic medic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Consultatie getTipConsutlatie() {
        return tipConsutlatie;
    }

    public void setTipConsutlatie(Consultatie tipConsutlatie) {
        this.tipConsutlatie = tipConsutlatie;
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
}
