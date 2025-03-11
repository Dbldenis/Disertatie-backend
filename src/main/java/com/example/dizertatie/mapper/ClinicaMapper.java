package com.example.dizertatie.mapper;

import com.example.dizertatie.dto.ClinicaDto;
import com.example.dizertatie.entities.Clinica;


public class ClinicaMapper {

    public static Clinica clinica2Entity(ClinicaDto clinicaDto) {

        if (clinicaDto.getId() == null) {
            return null;
        }

        Clinica clinicaCreata = new Clinica();

        clinicaCreata.setId(clinicaDto.getId());
        clinicaCreata.setNume(clinicaDto.getNume());
        clinicaCreata.setAdresa(clinicaDto.getAdresa());
        clinicaCreata.setListaMedici(clinicaDto.getListaMedici());

        return clinicaCreata;

    }

    public static ClinicaDto clinica2Dto(Clinica clinica) {

        if (clinica.getId() == null) {
            return null;
        }

        ClinicaDto clinicaDto = new ClinicaDto();

        clinicaDto.setId(clinica.getId());
        clinicaDto.setNume(clinicaDto.getNume());
        clinicaDto.setAdresa(clinica.getAdresa());
        clinicaDto.setListaMedici(clinica.getListaMedici());

        return clinicaDto;

    }
}
