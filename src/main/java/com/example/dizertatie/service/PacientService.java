package com.example.dizertatie.service;

import com.example.dizertatie.entities.Medic;
import com.example.dizertatie.entities.Pacient;
import com.example.dizertatie.repository.MedicRepository;
import com.example.dizertatie.repository.PacientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.InputMismatchException;

@Service
public class PacientService {

    @Autowired
    private PacientRepository pacientRepository;

    @Autowired
    private MedicRepository medicRepository;


    private String encodePassword(String password) {
        String encodedPassword = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            encodedPassword = Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return encodedPassword;
    }

    public Pacient login(Pacient pacient) {

        Pacient existentPacient = pacientRepository.findByEmail(pacient.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User with email " + pacient.getEmail() + " not found"));

        String encodedPassword = encodePassword(pacient.getParola());
        if (!existentPacient.isVerified() || !encodedPassword.equals(existentPacient.getParola())) {
            throw new InputMismatchException();
        }
        return existentPacient;
    }

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
