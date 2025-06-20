package com.example.dizertatie.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PacientDto {

    // nume, prenume, email, telefon, cnp, adresa, asigurare, medic
    private Long id;
    private String nume;
    private String prenume;
    private String utilizator;
    private String email;
    private String parola;
    private Boolean isVerified;
    private LocalDateTime verificationCodeGenerationTime;
    private LocalDateTime codVerificareGenerareTimp;
    private String telefon;
    private String cnp;
    private String adresa;
    private Boolean asigurare;
    private Long  medicId;
    private ProgramareDto programareDto;
    private FisaPacientuluiDto fisaPacientuluiDto;

}
