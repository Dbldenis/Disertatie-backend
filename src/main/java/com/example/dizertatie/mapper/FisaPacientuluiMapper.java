package com.example.dizertatie.mapper;

import com.example.dizertatie.dto.FisaPacientuluiDto;
import com.example.dizertatie.entities.FisaPacientului;

public class FisaPacientuluiMapper {

    // Conversie DTO -> Entitate (fără medicamenteConcomitente, dataNasterii, sex)
    public static FisaPacientului fisaPacientuluiDto2Entity(FisaPacientuluiDto fisaDto) {

        FisaPacientului fisa = new FisaPacientului();

        /*fisa.setPacient(fisaDto.getPacient());
        fisa.setMedic(fisaDto.getMedic());*/

        fisa.setId(fisaDto.getId());
        fisa.setGreutate(fisaDto.getGreutate());
        fisa.setInaltime(fisaDto.getInaltime());
        fisa.setAlergii(fisaDto.getAlergii());
        fisa.setReactiiAdverse(fisaDto.getReactiiAdverse());
        fisa.setMedicamenteSuspecte(fisaDto.getMedicamenteSuspecte());
        fisa.setEvolutieReactie(fisaDto.getEvolutieReactie());
        fisa.setNumeRaportor(fisaDto.getNumeRaportor());
        fisa.setRelatieCuPacientul(fisaDto.getRelatieCuPacientul());

        // Pacientul se setează separat în serviciu (prin ID)
        return fisa;
    }

    // Conversie Entitate -> DTO
    public static FisaPacientuluiDto fisaPacientuluiEntity2Dto(FisaPacientului fisa) {

        if (fisa.getId() == null) {
            return null;
        }

        FisaPacientuluiDto fisaDto = new FisaPacientuluiDto();

        fisaDto.setId(fisa.getId());
        fisaDto.setGreutate(fisa.getGreutate());
        fisaDto.setInaltime(fisa.getInaltime());
        fisaDto.setAlergii(fisa.getAlergii());
        fisaDto.setReactiiAdverse(fisa.getReactiiAdverse());
        fisaDto.setMedicamenteSuspecte(fisa.getMedicamenteSuspecte());
        fisaDto.setEvolutieReactie(fisa.getEvolutieReactie());
        fisaDto.setNumeRaportor(fisa.getNumeRaportor());
        fisaDto.setRelatieCuPacientul(fisa.getRelatieCuPacientul());

        // Legătura cu pacientul (prin ID)
        /*if (fisa.getPacient() != null) {
            fisaDto.setPacient(fisa.getPacient());
        }*/

        return fisaDto;
    }
}