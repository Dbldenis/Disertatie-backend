package com.example.dizertatie.dto;

import com.example.dizertatie.entities.TipConsultatie;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class ProgramareDto {

    private Long Id;
    private TipConsultatie tipConsultatie;
    private LocalDate data;
    private LocalTime ora;

    private PacientDto pacientDto;
    private MedicDto medicDto;
    /*private Long pacientId;
    private Long medicId;*/

}
