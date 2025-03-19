package com.example.dizertatie.dto;


import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
//import javax.validation.constraints.NotNull;

@Data
public class FisaPacientuluiDto {
    // Câmpuri obligatorii/opționale (fără dataNasterii, sex, medicamenteConcomitente)

    //@NotNull(message = "Greutatea este obligatorie")
    private Double greutate;

    //@NotNull(message = "Înălțimea este obligatorie")
    private Double inaltime;

    private String alergii;
    private String reactiiAdverse;
    private String medicamenteSuspecte;
    private String evolutieReactie;
    private String numeRaportor;
    private String relatieCuPacientul;

    // Legătura cu pacientul (prin ID)
    //@NotNull(message = "ID-ul pacientului este obligatoriu")
    private Long pacientId;
}