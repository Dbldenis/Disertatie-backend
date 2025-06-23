package com.example.dizertatie.service;

import com.example.dizertatie.dto.ConsultatieDto;
import com.example.dizertatie.entities.Consultatie;
import com.example.dizertatie.entities.FisaPacientului;
import com.example.dizertatie.entities.Pacient;
import com.example.dizertatie.mapper.ConsultatiMapper;
import com.example.dizertatie.repository.ConsultatiRepository;
import com.example.dizertatie.repository.PacientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultatieService {


    @Autowired
    private ConsultatiRepository consultatiRepository;

    @Autowired
    private PacientRepository pacientRepository;


    @Transactional
    public Consultatie createConsultatie(ConsultatieDto consultatieDto, Long pacientId) {

        // Verifică existența pacientului
        Pacient pacient = pacientRepository.findById(pacientId)
                .orElseThrow(() -> new EntityNotFoundException("Pacient not found"));

        Consultatie consultatie = ConsultatiMapper.consultati2Entity(consultatieDto);
        consultatie.setFisaPacientului(pacient.getFisaPacientului());

        // Asociază consultația cu fișa pacientului (dacă este necesar)
        FisaPacientului fisa = pacient.getFisaPacientului();

        fisa.getListaConsultati().add(consultatie); // Dacă relația este implementată

        //pacientRepository.save(pacient);

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

    public List<Consultatie> getConsultatiiByMedicIdAndPacientId(Long medicId, Long pacientId) {
        return consultatiRepository.findByMedicIdAndPacientId(medicId, pacientId);
    }


    public void deleteConsultatie(Long consultatieId) {
        consultatiRepository.deleteById(consultatieId);
    }

    public void deleteAllConsultati() {
        consultatiRepository.deleteAll();
    }

}
