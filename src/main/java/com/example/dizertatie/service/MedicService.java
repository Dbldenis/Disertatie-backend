package com.example.dizertatie.service;

import com.example.dizertatie.mapper.ConsultatiMapper;
import com.example.dizertatie.dto.ConsultatieDto;
import com.example.dizertatie.entities.Consultatie;
import com.example.dizertatie.entities.FisaPacientului;
import com.example.dizertatie.entities.Medic;
import com.example.dizertatie.entities.Pacient;
import com.example.dizertatie.repository.ConsultatiRepository;
import com.example.dizertatie.repository.FisaPacientuluiRepository;
import com.example.dizertatie.repository.MedicRepository;
import com.example.dizertatie.repository.PacientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    @Autowired
    private ConsultatiRepository consultatiRepository;

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

    @Transactional
    public Consultatie createConsultatie(ConsultatieDto consultatieDto, Long pacientId) {

        // Verifică existența pacientului
        Pacient pacient = pacientRepository.findById(pacientId)
                .orElseThrow(() -> new EntityNotFoundException("Pacient not found"));

        Consultatie consultatie = ConsultatiMapper.consultati2Entity(consultatieDto);
        consultatie.setFisaPacientului(pacient.getFisaPacientului());

        // Asociază consultația cu fișa pacientului (dacă este necesar)
        FisaPacientului fisa = pacient.getFisaPacientului();
        if(fisa != null) {
            fisa.getListaConsultati().add(consultatie); // Dacă relația este implementată
        }

        consultatiRepository.save(consultatie);

        return consultatie;
    }

    public Consultatie consultatieUpdate(ConsultatieDto consultatieToUpdate, Long consultatieId) {

        Consultatie existing = consultatiRepository.findById(consultatieId)
                .orElseThrow(() -> new RuntimeException("Consultația cu id-ul " + consultatieId + " nu a fost găsită."));

        // Actualizează câmpurile permise (de exemplu: data, simptome, tratament, diagnostic, observatii)
        existing.setDataConsultatiei(consultatieToUpdate.getDataConsultatiei());
        existing.setSimptome(consultatieToUpdate.getSimptome());
        existing.setTratament(consultatieToUpdate.getTratament());
        existing.setDiagnostic(consultatieToUpdate.getDiagnostic());
        existing.setObservati(consultatieToUpdate.getObservati());

        return consultatiRepository.save(existing);
    }

    public Consultatie getConsultatieById(Long consultatieId) {
        return consultatiRepository.findById(consultatieId)
                .orElseThrow(() -> new RuntimeException("Consultația cu id " + consultatieId + " nu a fost găsită."));
    }


    public void deleteAllPacienti () {
        medicRepository.deleteAll();
    }


}
