package com.example.dizertatie.mapper;

import com.example.dizertatie.dto.MedicDto;
import com.example.dizertatie.entities.Medic;

public class MedicMapper {

    public static Medic medic2Entity(MedicDto medicDto) {

        Medic medicCreat = new Medic();

        // nume, prem, specializare, telefon, codparaf, listapacienti, consultati,

        medicCreat.setNume(medicDto.getNume());
        medicCreat.setPrenume(medicDto.getPrenume());
        medicCreat.setSpecializare(medicDto.getSpecializare());
        medicCreat.setTelefon(medicDto.getTelefon());
        medicCreat.setCodParafa(medicDto.getCodParafa());
        medicCreat.setParola(medicDto.getParola());

        medicCreat.setEmail(medicDto.getEmail());
        medicCreat.setEsteVerificat(medicDto.getIsVerified() != null ? medicDto.getIsVerified() : false); // modificat
        medicCreat.setEsteVerificat(medicDto.getIsVerified());
        medicCreat.setCodVerificare(medicDto.getCodVerificare());
        medicCreat.setCodVerificareGenerareTimp(medicDto.getCodVerificareGenerareTimp());

        //medicCreat.setListaPacienti(medicDto.getListaPacienti(medicDto.getListaPacienti());


        return medicCreat;
    }

    public static Medic medic2Dto(Medic medic) {

        if (medic.getId() == null) {
            return null;
        }

        // nume, prem, specializare, telefon, codparaf, listapacienti, consultati,

        Medic medicReturnat = new Medic();

        medicReturnat.setNume(medic.getNume());
        medicReturnat.setPrenume(medic.getPrenume());
        medicReturnat.setSpecializare(medic.getSpecializare());
        medicReturnat.setTelefon(medic.getTelefon());
        medicReturnat.setCodParafa(medic.getCodParafa());
        medicReturnat.setParola(medic.getParola());

        medicReturnat.setEmail(medic.getEmail());
        medicReturnat.setEsteVerificat(medic.isEsteVerificat());
        medicReturnat.setCodVerificareGenerareTimp(medic.getCodVerificareGenerareTimp());


        //medicReturnat.getListaPacienti(medic.getListaPacienti());
        //medicReturnat.getConsultati(medic.getConsultati());


        return medicReturnat;
    }
}
