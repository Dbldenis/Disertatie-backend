package com.example.dizertatie.service;

import com.example.dizertatie.entities.FisaPacientului;
import com.example.dizertatie.entities.Medic;
import com.example.dizertatie.entities.Pacient;
import com.example.dizertatie.repository.FisaPacientuluiRepository;
import com.example.dizertatie.repository.MedicRepository;
import com.example.dizertatie.repository.PacientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class FisaPacientuluiService {

    @Autowired
    private PacientRepository pacientRepository;

    @Autowired
    private MedicRepository medicRepository;

    @Autowired
    private FisaPacientuluiRepository fisaPacientuluiRepository;

    public FisaPacientului creeazaFisa(FisaPacientului fisaPacientului, @PathVariable Long pacientId, @PathVariable Long medicId) {

        Pacient pacient = pacientRepository.findById(pacientId)
                .orElseThrow(EntityNotFoundException::new);

        Medic medic = medicRepository.findById(medicId)
                .orElseThrow(EntityNotFoundException::new);// luam medicul de la pacient.

        FisaPacientului fisaCreata = new FisaPacientului();

        fisaCreata.setPacient(pacient);
        pacient.setFisaPacientului(fisaCreata); // bidirectional

        fisaCreata.setMedic(medic);

        fisaCreata.setGreutate(fisaPacientului.getGreutate());
        fisaCreata.setInaltime(fisaPacientului.getInaltime());
        fisaCreata.setAlergii(fisaPacientului.getAlergii());
        fisaCreata.setEvolutieReactie(fisaPacientului.getEvolutieReactie());
        fisaCreata.setReactiiAdverse(fisaPacientului.getReactiiAdverse());
        fisaCreata.setNumeRaportor(fisaPacientului.getNumeRaportor());
        fisaCreata.setMedicamenteSuspecte(fisaPacientului.getMedicamenteSuspecte());
        fisaCreata.setRelatieCuPacientul(fisaPacientului.getRelatieCuPacientul());

        return fisaPacientuluiRepository.save(fisaCreata);
    }
}
