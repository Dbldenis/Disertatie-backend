package com.example.dizertatie.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MedicDto {

    // nume, prenume, specializare, telefon, codparafa, parola, email
    // este verificat, cod verificare, cod verificare generare timp
    private String nume;
    private String prenume;
    private String specializare;
    private String telefon;
    private Integer codParafa;
    private String parola;
    private String email;
    private Integer aniExperienta;
    private Boolean isVerified = false;
    private String codVerificare;
    private LocalDateTime codVerificareGenerareTimp;
}
//private List<PacientDto> listaPacienti = new ArrayList<>();