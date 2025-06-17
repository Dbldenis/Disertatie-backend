package com.example.dizertatie.service;

import com.example.dizertatie.dto.FisaPacientuluiDto;
import com.example.dizertatie.entities.FisaPacientului;
import com.example.dizertatie.entities.Medic;
import com.example.dizertatie.entities.Pacient;
import com.example.dizertatie.repository.FisaPacientuluiRepository;
import com.example.dizertatie.repository.MedicRepository;
import com.example.dizertatie.repository.PacientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    public FisaPacientului creeazaFisa(FisaPacientului fisaPacientului, Long pacientId, Long medicId) {

        Pacient pacient = pacientRepository.findById(pacientId)
                .orElseThrow(EntityNotFoundException::new);

        Medic medic = medicRepository.findById(medicId)
                .orElseThrow(EntityNotFoundException::new);// luam medicul de la pacient.

        FisaPacientului fisaCreata = new FisaPacientului();

        fisaCreata.setPacient(pacient);
        pacient.setFisaPacientului(fisaCreata); // bidirectional

        fisaCreata.setMedic(medic);

        //medic.getListaFisaPacienti().add(fisaCreata);
        //medic.addFisa(fisaCreata); // bidirectional

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

    public FisaPacientului updateFisa(FisaPacientuluiDto fisaPacientuluiDto, @PathVariable Long fisaId) {

        FisaPacientului existingFisa = fisaPacientuluiRepository.findById(fisaId)
                .orElseThrow(EntityNotFoundException::new);

        // Mapăm datele din DTO peste entitate
        existingFisa.setGreutate(fisaPacientuluiDto.getGreutate());
        existingFisa.setInaltime(fisaPacientuluiDto.getInaltime());
        existingFisa.setAlergii(fisaPacientuluiDto.getAlergii());
        existingFisa.setReactiiAdverse(fisaPacientuluiDto.getReactiiAdverse());
        existingFisa.setMedicamenteSuspecte(fisaPacientuluiDto.getMedicamenteSuspecte());
        existingFisa.setEvolutieReactie(fisaPacientuluiDto.getEvolutieReactie());
        existingFisa.setNumeRaportor(fisaPacientuluiDto.getNumeRaportor());
        existingFisa.setRelatieCuPacientul(fisaPacientuluiDto.getRelatieCuPacientul());

        // Salvezi entitatea actualizată
        //FisaPacientului updatedFisa = fisaRepository.save(existingFisa);

        return fisaPacientuluiRepository.save(existingFisa);
    }

    public FisaPacientului getFisa(Long fisaId) {

        return fisaPacientuluiRepository.findById(fisaId)
                .orElseThrow(EntityNotFoundException::new);
    }

    /*@Transactional
    public void stergePacientCuFise(Long pacientId) {

        // Șterge toate fișele asociate
        fisaPacientuluiRepository.deleteByPacientId(pacientId); // query
        // Apoi șterge pacientul
        pacientRepository.deleteById(pacientId);
    }*/

    /*@Transactional
    public void stergeFisaDupaId(Long fisaId) {
        FisaPacientului fisa = fisaPacientuluiRepository.findById(fisaId)
                .orElseThrow(() -> new RuntimeException("Fișa pacientului inexistentă"));
        Pacient pacient = fisa.getPacient();
        if (pacient != null) {
            pacient.setFisaPacientului(null);    // Rupe legătura
            pacientRepository.save(pacient);     // Salvează modificarea la pacient
        }
        fisaPacientuluiRepository.deleteById(fisaId); // Acum se poate șterge fișa fără blocaj DB
    }*/

    @Transactional
    public void deletePacientById(Long pacientId) {
        // Șterge mai întâi rândurile asociate din tabelul „fisa_pacientului”
        pacientRepository.deleteById(pacientId);

        fisaPacientuluiRepository.deleteByPacientId(pacientId);

        // Apoi șterge rândul din tabelul „pacient”

    }



}
