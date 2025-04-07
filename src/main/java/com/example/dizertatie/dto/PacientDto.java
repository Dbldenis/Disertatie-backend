package com.example.dizertatie.dto;

import com.example.dizertatie.entities.FisaPacientului;
import com.example.dizertatie.entities.Medic;
import lombok.Data;

@Data
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
    private Long  medicId;
    //private FisaPacientului fisaPacientului;

}
