package com.example.dizertatie.service;

import com.example.dizertatie.entities.Medic;
import com.example.dizertatie.entities.Pacient;
import com.example.dizertatie.repository.MedicRepository;
import com.example.dizertatie.repository.PacientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PacientService {

    @Autowired
    private PacientRepository pacientRepository;

    @Autowired
    private MedicRepository medicRepository;

    /*public Pacient verify(String email, String verificationCode) {

        Pacient pacient = pacientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getVerificationCodeExpiration() == null || LocalDateTime.now().isAfter(user.getVerificationCodeExpiration())) {
            throw new RuntimeException("Verification code has expired.");
        }

        if (!user.getVerificationCode().equals(verificationCode)) {
            throw new RuntimeException("Invalid verification code.");
        }

        user.setVerifiedAccount(true);
        user.setVerificationCode(null);
        user.setVerificationCodeExpiration(null);

        return userRepository.save(user);
    }*/

    public Pacient pacientToCreate(Pacient pacientToCreate, Long medicId) {

        if (pacientToCreate.getId() != null) {
            throw new RuntimeException("You cannot provide an ID to a new pacient that you want to create");
        }

        Medic medicCreated = medicRepository.findById(medicId)
                .orElseThrow(EntityNotFoundException::new);

        medicCreated.addPacient(pacientToCreate);

        return pacientRepository.save(pacientToCreate);
    }

    public Pacient pacientUpdate(Pacient pacientUpdate, Long pacientId) {

        Pacient dbPacient = pacientRepository.findById(pacientId)
                .orElseThrow(EntityNotFoundException::new);

        // nume, prenume, email, telefon, cnp, adresa, asigurare, medic
        dbPacient.setNume(pacientUpdate.getNume());
        dbPacient.setPrenume(pacientUpdate.getPrenume());
        dbPacient.setEmail(pacientUpdate.getEmail());
        dbPacient.setAdresa(pacientUpdate.getAdresa());
        dbPacient.setTelefon(pacientUpdate.getTelefon());
        dbPacient.setCnp(pacientUpdate.getCnp());
        dbPacient.setAsigurare(pacientUpdate.getAsigurare());
        dbPacient.setMedic(pacientUpdate.getMedic());

        return pacientRepository.save(dbPacient);

    }



    //GET Pacient by Id
    public Pacient getPacientData(Long pacientId) {

        return pacientRepository.findById(pacientId).
                orElseThrow(EntityNotFoundException::new);
    }


    public void deletePacient(Long pacientId) {
        pacientRepository.deleteById(pacientId);
    }

    public void deleteALL() {
        pacientRepository.deleteAll();
    }

}
