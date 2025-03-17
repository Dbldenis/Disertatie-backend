package com.example.dizertatie.mapper;

import com.example.dizertatie.dto.MedicDto;
import com.example.dizertatie.dto.PacientDto;
import com.example.dizertatie.entities.Medic;
import com.example.dizertatie.entities.Pacient;

public class PacientMapper {

    // nume, prenume, email, telefon, cnp, adresa, asigurare, medic

    public static Pacient pacient2Entity(PacientDto pacientDto) {

        Pacient pacient = new Pacient();

        pacient.setNume(pacientDto.getNume());
        pacient.setPrenume(pacientDto.getPrenume());
        pacient.setEmail(pacientDto.getEmail());
        pacient.setTelefon(pacientDto.getTelefon());
        pacient.setCnp(pacientDto.getCnp());
        pacient.setAdresa(pacientDto.getAdresa());
        pacient.setAsigurare(pacientDto.getAsigurare());
        pacient.setMedic(pacient.getMedic());

        return pacient;

    }

    public static PacientDto pacient2Dto(Pacient pacient) {

        if (pacient.getId() == null) {
            return null;
        }

        PacientDto pacientDto = new PacientDto();

        // nume, prenume, email, telefon, cnp, adresa, asigurare, medic

        pacientDto.setNume(pacient.getNume());
        pacientDto.setPrenume(pacient.getPrenume());
        pacientDto.setEmail(pacient.getEmail());
        pacientDto.setTelefon(pacient.getTelefon());
        pacientDto.setCnp(pacient.getCnp());
        pacientDto.setAdresa(pacient.getAdresa());
        pacientDto.setMedic(pacient.getMedic());

        return pacientDto;

    }

}














