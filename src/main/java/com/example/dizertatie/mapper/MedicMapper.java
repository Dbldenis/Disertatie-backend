package com.example.dizertatie.mapper;

import com.example.dizertatie.dto.MedicDto;
import com.example.dizertatie.entities.Medic;

public class MedicMapper {

    public static Medic medic2Entity(MedicDto medicDto) {

        if (medicDto.getId() == null) {
            return null;
        }

        Medic medicCreat = new Medic();

        // nume, prem, specializare, telefon, codparaf, listapacienti, consultati,

        medicCreat.setId(medicDto.getId());
        medicCreat.setNume(medicDto.getNume());
        medicCreat.setPrenume(medicDto.getPrenume());
        medicCreat.setSpecializare(medicDto.getSpecializare());
        medicCreat.setTelefon(medicDto.getTelefon());
        medicCreat.setCodParafa(medicDto.getCodParafa());
        //medicCreat.setListaPacienti(medicDto.getListaPacienti(medicDto.getListaPacienti());
        medicCreat.setConsultati(medicDto.getConsultati());

        return medicCreat;
    }

    public static MedicDto medic2Dto(Medic medic) {

        if (medic.getId() == null) {
            return null;
        }

        // nume, prem, specializare, telefon, codparaf, listapacienti, consultati,

        MedicDto medicReturnat = new MedicDto();

        medicReturnat.setId(medic.getId());
        medicReturnat.setNume(medic.getNume());
        medicReturnat.setPrenume(medic.getPrenume());
        medicReturnat.setSpecializare(medic.getSpecializare());
        medicReturnat.setTelefon(medic.getTelefon());
        medicReturnat.setCodParafa(medic.getCodParafa());
        //medicReturnat.getListaPacienti(medic.getListaPacienti());
        //medicReturnat.getConsultati(medic.getConsultati());


        return medicReturnat;
    }
}
