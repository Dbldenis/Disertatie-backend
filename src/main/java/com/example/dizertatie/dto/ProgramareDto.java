package com.example.dizertatie.dto;

import com.example.dizertatie.entities.Consultatie;
import com.example.dizertatie.entities.Medic;
import com.example.dizertatie.entities.Pacient;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ProgramareDto {

    private Long Id;
    private Consultatie tipConsultatie;
    private LocalDate data;
    private LocalTime ora;
    private Pacient pacient;
    private Medic medic;

}
