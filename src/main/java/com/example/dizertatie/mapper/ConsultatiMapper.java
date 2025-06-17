package com.example.dizertatie.mapper;

import com.example.dizertatie.dto.ConsultatieDto;
import com.example.dizertatie.entities.Consultatie;

public class ConsultatiMapper {

    public static Consultatie consultati2Entity(ConsultatieDto consultatiDto) {

        Consultatie consultati = new Consultatie();

        //// id, data, observati, simptome, tratament, diagnostic, fisa pacientului

        consultati.setDataConsultatiei(consultatiDto.getDataConsultatiei());
        consultati.setObservati(consultatiDto.getObservati());
        consultati.setSimptome(consultatiDto.getSimptome());
        consultati.setTratament(consultatiDto.getTratament());
        consultati.setDiagnostic(consultatiDto.getDiagnostic());

        consultati.setFisaPacientului(consultati.getFisaPacientului());

        return consultati;
    }

    public static ConsultatieDto consultatie2Dto(Consultatie consultatie) {

        if (consultatie.getId() == null) {
            return null;
        }

        ConsultatieDto consultatiDto = new ConsultatieDto();

        // id, data, observati, simptome, tratament, diagnostic, pacient

        consultatiDto.setId(consultatie.getId());
        consultatiDto.setDataConsultatiei(consultatie.getDataConsultatiei());
        consultatiDto.setObservati(consultatie.getObservati());
        consultatiDto.setSimptome(consultatie.getSimptome());
        consultatiDto.setTratament(consultatie.getTratament());
        consultatiDto.setDiagnostic(consultatie.getDiagnostic());

        //consultatiDto.setFisaPacientuluiDto(consultatie.getFisaPacientului());
        //consultatiDto.setPacient(consultatie.getPacient());

        return consultatiDto;
    }

}

