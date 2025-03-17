package com.example.dizertatie.dto;

import com.example.dizertatie.entities.Consultatie;
import com.example.dizertatie.entities.Medic;
import com.example.dizertatie.entities.Pacient;

public class ProgramareDto {

    private Long Id;
    private Consultatie tipConsultatie;
    private Pacient pacient;
    private Medic medic;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Consultatie getTipConsultatie() {
        return tipConsultatie;
    }

    public void setTipConsultatie(Consultatie tipConsultatie) {
        this.tipConsultatie = tipConsultatie;
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
