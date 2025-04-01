package com.example.dizertatie.service;

import com.example.dizertatie.dto.ProgramareDto;
import com.example.dizertatie.entities.Medic;
import com.example.dizertatie.entities.Pacient;
import com.example.dizertatie.entities.Programare;
import com.example.dizertatie.repository.MedicRepository;
import com.example.dizertatie.repository.PacientRepository;
import com.example.dizertatie.repository.ProgramareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ProgramareService {

    @Autowired
    private ProgramareRepository programareRepository;

    @Autowired
    private PacientRepository pacientRepository;

    @Autowired
    private MedicRepository medicRepository;

    public Programare creazaProgramare(Programare programare, Long pacientId, Long medicId) {

        if (programare.getData() == null || programare.getOra() == null) {
            throw new RuntimeException("Data și ora sunt obligatorii");
        }

        LocalDate azi = LocalDate.now();
        LocalTime acum = LocalTime.now();

        if (programare.getData().isBefore(azi) || (programare.getData().isEqual(azi) && programare.getOra().isBefore(acum))) {
            throw new RuntimeException("Nu poți crea o programare în trecut");
        }

        Pacient pacient = pacientRepository.findById(pacientId)
                .orElseThrow(() -> new RuntimeException("Pacient inexistent"));
        Medic medic = medicRepository.findById(medicId)
                .orElseThrow(() -> new RuntimeException("Medic inexistent"));

        Programare programareCreata = new Programare();

        programareCreata.setPacient(pacient);
        programareCreata.setMedic(medic);
        programareCreata.setData(programare.getData());
        programareCreata.setOra(programare.getOra());
        programareCreata.setTipConsutlatie(programare.getTipConsutlatie());

        System.out.println("Programare ce va fi salvată: " + programareCreata);

        return programareRepository.save(programareCreata);
    }

    public Programare editeazaProgramare(Long programareId, ProgramareDto dto) {

        Programare programare = programareRepository.findById(programareId)
                .orElseThrow(() -> new RuntimeException("Programare inexistentă"));


        if (dto.getData() == null || dto.getOra() == null) {
            throw new RuntimeException("Data și ora sunt obligatorii");
        }

        LocalDate azi = LocalDate.now();
        LocalTime acum = LocalTime.now();

        if (dto.getData().isBefore(azi) ||
                (dto.getData().isEqual(azi) && dto.getOra().isBefore(acum))) {
            throw new RuntimeException("Nu poți seta programarea într-un moment din trecut");
        }

        // actualizăm doar câmpurile permise
        programare.setData(dto.getData());
        programare.setOra(dto.getOra());
        programare.setTipConsutlatie(dto.getTipConsultatie());

        return programareRepository.save(programare);
    }


    public void stergeProgramare(Long id) {
        if (!programareRepository.existsById(id)) {
            throw new RuntimeException("Programare inexistenta");
        }
        programareRepository.deleteById(id);
    }
}
