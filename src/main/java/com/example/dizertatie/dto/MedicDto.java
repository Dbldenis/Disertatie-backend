package com.example.dizertatie.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MedicDto {

    // nume, prem, specializare, telefon, codparaf, listapacienti, consultati,
    private String nume;
    private String prenume;
    private String specializare;
    private String telefon;
    private Integer codParafa;
    private List<PacientDto> listaPacienti = new ArrayList<>();
}
