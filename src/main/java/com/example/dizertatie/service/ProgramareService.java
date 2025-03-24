package com.example.dizertatie.service;

import com.example.dizertatie.entities.Medic;
import com.example.dizertatie.entities.Pacient;
import com.example.dizertatie.entities.Programare;
import com.example.dizertatie.repository.MedicRepository;
import com.example.dizertatie.repository.PacientRepository;
import com.example.dizertatie.repository.ProgramareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgramareService {

    @Autowired
    private ProgramareRepository programareRepository;

    @Autowired
    private PacientRepository pacientRepository;

    @Autowired
    private MedicRepository medicRepository;

    public Programare creazaProgramare(Programare programare) {

        Pacient pacient = pacientRepository.findById(programare.getPacient().getId())
                .orElseThrow(() -> new RuntimeException("Pacient inexistent"));
        Medic medic = medicRepository.findById(programare.getMedic().getId())
                .orElseThrow(() -> new RuntimeException("Medic inexistent"));

        Programare programareCreata = new Programare();
        programareCreata.setPacient(pacient);
        programareCreata.setMedic(medic);
        programareCreata.setData(programare.getData());
        programareCreata.setOra(programare.getOra());

        return programareRepository.save(programare);
    }

    /*public void stergeProgramare(Long id) {
        if (!programareRepository.existsById(id)) {
            throw new RuntimeException("Programare inexistenta");
        }
        programareRepository.deleteById(id);
    }*/
}
