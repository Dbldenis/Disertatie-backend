package com.example.dizertatie.dto;


import com.example.dizertatie.entities.Medic;
import com.example.dizertatie.entities.Pacient;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;
//import javax.validation.constraints.NotNull;

@Data
public class FisaPacientuluiDto {

    private Long id;
    private Double greutate;
    private Double inaltime;
    private String alergii;
    private String reactiiAdverse;
    private String medicamenteSuspecte;
    private String evolutieReactie;
    private String numeRaportor;
    private String relatieCuPacientul;
    private PacientDto pacientDto;
    private MedicDto medicDto;

    //private List<ConsultatieDto> listaConsultati;
    //@NotNull(message = "ID-ul pacientului este obligatoriu")
}