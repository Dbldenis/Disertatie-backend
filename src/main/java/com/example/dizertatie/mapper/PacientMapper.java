package com.example.dizertatie.mapper;

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
        pacient.setParola(pacientDto.getParola());

        pacient.setEsteVerificat(
                pacientDto.getIsVerified() != null ? pacientDto.getIsVerified() : false
        );

        pacient.setCodVerificareGenerareTimp(pacientDto.getVerificationCodeGenerationTime());

        pacient.setCodVerificareGenerareTimp(pacientDto.getCodVerificareGenerareTimp());
        pacient.setTelefon(pacientDto.getTelefon());
        pacient.setCnp(pacientDto.getCnp());
        pacient.setAdresa(pacientDto.getAdresa());
        pacient.setAsigurare(pacientDto.getAsigurare());

        /*Medic medic = new Medic();
        medic.setId(pacientDto.getMedicId());
        pacient.setMedic(medic);*/

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
        pacientDto.setParola(pacient.getParola());
        pacientDto.setIsVerified(pacient.isEsteVerificat());
        pacientDto.setVerificationCodeGenerationTime(pacient.getCodVerificareGenerareTimp());

        pacientDto.setCodVerificareGenerareTimp(pacient.getCodVerificareGenerareTimp());
        pacientDto.setTelefon(pacient.getTelefon());
        pacientDto.setCnp(pacient.getCnp());
        pacientDto.setAdresa(pacient.getAdresa());
        pacientDto.setAsigurare(pacient.getAsigurare());
        //pacientDto.setMedicId(pacient.getMedic().getId());

        if (pacient.getMedic() != null) {
            pacientDto.setMedicId(pacient.getMedic().getId());
        }


        return pacientDto;

    }

}














