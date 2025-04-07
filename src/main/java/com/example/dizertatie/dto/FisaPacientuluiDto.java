package com.example.dizertatie.dto;


import com.example.dizertatie.entities.Medic;
import com.example.dizertatie.entities.Pacient;
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

    private PacientDto pacientDto;
    private MedicDto medicDto;
    // pune dto-ul fiecarei entitati.

    private String alergii;
    private String reactiiAdverse;
    private String medicamenteSuspecte;
    private String evolutieReactie;
    private String numeRaportor;
    private String relatieCuPacientul;
    //@NotNull(message = "ID-ul pacientului este obligatoriu")
}