package com.example.dizertatie.mapper;

import com.example.dizertatie.dto.PacientDto;
import com.example.dizertatie.dto.ProgramareDto;
import com.example.dizertatie.entities.Programare;

public class ProgramareMapper {


    public static Programare programare2Entity(ProgramareDto programareDto) {

        Programare programare = new Programare();

        programare.setTipConsutlatie(programareDto.getTipConsultatie());
        programare.setData(programareDto.getData());
        programare.setOra(programareDto.getOra());

        return programare;

    }

    public static ProgramareDto exemplarToDTO(Programare programare) {

        if (programare.getId() == null) {
            return null;
        }

        ProgramareDto programareDto = new ProgramareDto();

        programareDto.setId(programare.getId());
        programareDto.setTipConsultatie(programare.getTipConsutlatie());
        programareDto.setData(programare.getData());
        programareDto.setOra(programare.getOra());

        return programareDto;

    }
}
