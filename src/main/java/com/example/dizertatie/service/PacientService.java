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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;

@Service
public class PacientService {

    private static final Integer MINUTES_AVAILABLE_CODE = 5;

    @Autowired
    private PacientRepository pacientRepository;

    @Autowired
    private MedicRepository medicRepository;

    @Autowired
    private EmailService emailService;



    public Pacient verify(Long pacientId, Pacient updatedPacient) {
        Pacient pacient = pacientRepository.findById(pacientId)
                .orElseThrow(() -> new EntityNotFoundException("Pacientul cu ID " + pacientId + " nu a fost gasit"));

        LocalDateTime currentTime = LocalDateTime.now();
        Duration elapsedTime = Duration.between(pacient.getCodVerificareGenerareTimp(), currentTime);

        if (elapsedTime.toMinutes() > MINUTES_AVAILABLE_CODE) {
            pacient.setCodVerificare(null);
            pacientRepository.save(pacient);
            throw new IllegalStateException("Codul de verificare a expirat. Solicita un nou cod de verificare.");
        }

        if (!pacient.getCodVerificare().equals(updatedPacient.getCodVerificare())) {
            throw new IllegalStateException("Cod invalid.");
        }

        pacient.setEsteVerificat(true);
        pacient.setCodVerificare("gata");

        return pacientRepository.save(pacient);
    }

    public Pacient retrimiteCodVerificare(Long pacientId) {

        Pacient pacient = pacientRepository.findById(pacientId)
                .orElseThrow(() -> new EntityNotFoundException("Pacientul cu ID " + pacientId + " nu a fost gasit"));

        if (pacient.isEsteVerificat()) {
            return pacient;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        Duration elapsedTime = Duration.between(pacient.getCodVerificareGenerareTimp(), currentTime);

        if (elapsedTime.toMinutes() > MINUTES_AVAILABLE_CODE - 1) {
            pacient.setCodVerificare(genereazaCodVerificare());
            pacient.setCodVerificareGenerareTimp(LocalDateTime.now());
        }

        emailService.sendVerificationEmail(pacient.getEmail(), pacient.getCodVerificare());

        return pacientRepository.save(pacient);
    }

    public Pacient login(Pacient pacient) {

        Pacient existentPacient = pacientRepository.findByEmail(pacient.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User with email " + pacient.getEmail() + " not found"));

        String encodedPassword = codificareParola(pacient.getParola());
        if (!existentPacient.isEsteVerificat() || !encodedPassword.equals(existentPacient.getParola())) {
            throw new InputMismatchException();
        }
        return existentPacient;
    }

    private String genereazaCodVerificare() {
        Random random = new Random();
        int codul = 100000 + random.nextInt(900000); // Generate 6-digit code
        return String.valueOf(codul);
    }

    private String codificareParola(String parola) {
        String criptareParola = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(parola.getBytes(StandardCharsets.UTF_8));
            criptareParola = Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return criptareParola;
    }

    public Pacient pacientToCreate(Pacient pacientToCreate) {

        if (pacientToCreate.getId() != null) {
            throw new RuntimeException("You cannot provide an ID to a new pacient that you want to create");
        }

        if (pacientToCreate.getCodVerificare() != null) {
            throw new RuntimeException("You cannot provide a verification code to a user");
        }

        //pacientToCreate.getMedic().addPacient(pacientToCreate);

        pacientToCreate.setParola(pacientToCreate.getParola()); // am eliminat codificare parola
        String verificationCode = genereazaCodVerificare();
        pacientToCreate.setCodVerificare(verificationCode);

        //emailService.sendVerificationEmail(pacientToCreate.getEmail(), verificationCode);

        pacientToCreate.setCodVerificareGenerareTimp(LocalDateTime.now());

        return pacientRepository.save(pacientToCreate);

    }

    /*public Pacient pacientToCreate(Pacient pacientToCreate) {

        if (pacientToCreate.getId() != null) {
            throw new RuntimeException("You cannot provide an ID to a new user that you want to create");
        }
        return pacientRepository.save(pacientToCreate);
    }*/

    public Pacient pacientUpdate(Pacient pacientUpdate, Long pacientId) {

        Pacient dbPacient = pacientRepository.findById(pacientId)
                .orElseThrow(EntityNotFoundException::new);

        // nume, prenume, email, telefon, cnp, adresa, asigurare, medic

        dbPacient.setNume(pacientUpdate.getNume());
        dbPacient.setPrenume(pacientUpdate.getPrenume());
        dbPacient.setEmail(pacientUpdate.getEmail());

        dbPacient.setParola(pacientUpdate.getParola());
        dbPacient.setCodVerificare(pacientUpdate.getCodVerificare());
        dbPacient.setCodVerificareGenerareTimp(pacientUpdate.getCodVerificareGenerareTimp());
        dbPacient.setEsteVerificat(pacientUpdate.isEsteVerificat());

        dbPacient.setAdresa(pacientUpdate.getAdresa());
        dbPacient.setTelefon(pacientUpdate.getTelefon());
        dbPacient.setCnp(pacientUpdate.getCnp());
        dbPacient.setAsigurare(pacientUpdate.getAsigurare());
        dbPacient.setMedic(pacientUpdate.getMedic());

        return pacientRepository.save(dbPacient);

    }

    public List<Medic> getMediciBySpecialitate(String specialitate) {
        return medicRepository.findBySpecializareIgnoreCase(specialitate);
    }


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
