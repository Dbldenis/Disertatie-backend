package com.example.dizertatie.dto;

import com.example.dizertatie.entities.Medic;
import lombok.Data;

import java.util.List;

@Data
public class ClinicaDto {

    private Long id;
    private String nume;
    private String adresa;
    private List<Medic> listaMedici;

}
