package com.example.dizertatie.mapper;

import com.example.dizertatie.dto.PacientDto;
import com.example.dizertatie.dto.ProgramareDto;
import com.example.dizertatie.entities.Programare;

public class ProgramareMapper {


    public static Programare programare2Entity(ProgramareDto programareDto) {

        Programare programare = new Programare();

        //programare.setTipConsutlatie(programareDto.getTipConsultatie());
        programare.setData(programareDto.getData());
        programare.setOra(programareDto.getOra());

        programare.setPacient(programare.getPacient());
        programare.setMedic(programare.getMedic());
        programare.setId(programareDto.getId());


        return programare;

    }

    public static ProgramareDto exemplarToDTO(Programare programare) {

        if (programare.getId() == null) {
            return null;
        }

        ProgramareDto programareDto = new ProgramareDto();


        //programareDto.setTipConsultatie(programare.getTipConsutlatie());


        programareDto.setData(programare.getData());
        programareDto.setOra(programare.getOra());

        if (programare.getPacient() != null) {
            programareDto.setPacientDto(PacientMapper.pacient2Dto(programare.getPacient()));
        }

        if (programare.getMedic() != null) {
            programareDto.setMedicDto(MedicMapper.medic2Dto(programare.getMedic()));
        }

        /*programareDto.setPacientDto(programareDto.getPacientDto());
        programareDto.setMedicDto(programareDto.getMedicDto());*/

        programareDto.setId(programare.getId());
        return programareDto;

    }
}
