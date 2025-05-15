package com.example.dizertatie.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MedicDto {

    // nume, prem, specializare, telefon, codparaf, parola, email
    private String nume;
    private String prenume;
    private String specializare;
    private String telefon;
    private Integer codParafa;
    private String parola;
    private String email;
    private Boolean isVerified;
    private String codVerificare;
    private LocalDateTime codVerificareGenerareTimp;

    //private List<PacientDto> listaPacienti = new ArrayList<>();
}
