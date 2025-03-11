package com.example.dizertatie.mapper;

import com.example.dizertatie.dto.ConsultatiDto;
import com.example.dizertatie.entities.Consultati;

public class ConsultatiMapper {

    public static Consultati consultati2Entity(ConsultatiDto consultatiDto) {

        if (consultatiDto.getId() == null) {
            return null;
        }

        Consultati consultati = new Consultati();

        //// id, data, observati, simptome, tratament, diagnostic, pacient, medic

        consultati.setId(consultatiDto.getId());
        consultati.setDataConsultatiei(consultatiDto.getDataConsultatiei());
        consultati.setObservati(consultatiDto.getObservati());
        consultati.setSimptome(consultatiDto.getSimptome());
        consultati.setTratament(consultatiDto.getTratament());
        consultati.setDiagnostic(consultatiDto.getDiagnostic());
        consultati.setPacient(consultatiDto.getPacient());
        consultati.setMedic(consultatiDto.getMedic());

        return consultati;
    }

    public static ConsultatiDto consultatie2Dto(Consultati consultati) {

        if (consultati.getId() == null) {
            return null;
        }

        ConsultatiDto consultatiDto = new ConsultatiDto();

        // id, data, observati, simptome, tratament, diagnostic, pacient, medic

        consultatiDto.setId(consultati.getId());
        consultatiDto.setDataConsultatiei(consultati.getDataConsultatiei());
        consultatiDto.setObservati(consultati.getObservati());
        consultatiDto.setSimptome(consultati.getSimptome());
        consultatiDto.setTratament(consultati.getTratament());
        consultatiDto.setDiagnostic(consultati.getDiagnostic());
        consultatiDto.setPacient(consultati.getPacient());
        consultatiDto.setMedic(consultati.getMedic());

        return consultatiDto;
    }

}
