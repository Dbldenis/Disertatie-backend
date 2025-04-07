
package com.example.dizertatie.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ConsultatieDto {

    // id, data, observati, simptome, tratament, diagnostic, fisa pacientului
    private Long id;
    private LocalDateTime dataConsultatiei;
    private String observati;
    private String simptome;
    private String tratament;
    private String diagnostic;
    private FisaPacientuluiDto fisaPacientuluiDto;
    //private PacientDto pacientDto;
    //private TipConsultatie tipConsultatie;

}

