
package com.example.dizertatie.dto;

import com.example.dizertatie.entities.Medic;
import com.example.dizertatie.entities.Pacient;
import java.time.LocalDateTime;

import com.example.dizertatie.entities.TipConsultatie;
import lombok.Data;

@Data
public class ConsultatieDto {

    // id, data, observati, simptome, tratament, diagnostic, pacient
    private Long id;
    private LocalDateTime dataConsultatiei;
    private String observati;
    private String simptome;
    private String tratament;
    private String diagnostic;
    private Pacient pacient;
    //private TipConsultatie tipConsultatie;

}

