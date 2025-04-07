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

@Service
public class MedicService {

    @Autowired
    private MedicRepository medicRepository;

    @Autowired
    private PacientRepository pacientRepository;

    @Autowired
    private FisaPacientuluiRepository fisaPacientuluiRepository;

    public Medic medicToCreate(Medic medicToCreate) {

        if (medicToCreate.getId() != null) {
            throw new RuntimeException("You cannot provide an ID to a new user that you want to create");
        }
        return medicRepository.save(medicToCreate);
    }

    //GET Pacient by Id
    public Pacient getPacientData(Long pacientId) {

        return pacientRepository.findById(pacientId).
                orElseThrow(EntityNotFoundException::new);
    }

    public FisaPacientului FisaToCreate(FisaPacientului fisaPacientuluiToCreate, Long pacientId, Long medicId) {

        if (fisaPacientuluiToCreate.getId() != null) {
            throw new RuntimeException("You cannot provide an ID to a new user that you want to create");
        }

        Pacient pacientCreated = pacientRepository.findById(pacientId)
                .orElseThrow(EntityNotFoundException::new);

        pacientCreated.setFisaPacientului(fisaPacientuluiToCreate);
        fisaPacientuluiToCreate.setPacient(pacientCreated);


        Medic medic = medicRepository.findById(medicId).
                orElseThrow(EntityNotFoundException::new);

        fisaPacientuluiToCreate.setMedic(medic);


        return fisaPacientuluiRepository.save(fisaPacientuluiToCreate);
    }

    public void deleteAllPacienti () {
        medicRepository.deleteAll();
    }


}
