package com.example.dizertatie.service;

import com.example.dizertatie.dto.PacientDto;
import com.example.dizertatie.entities.FisaPacientului;
import com.example.dizertatie.entities.Medic;
import com.example.dizertatie.entities.Pacient;
import com.example.dizertatie.entities.Programare;
import com.example.dizertatie.mapper.FisaPacientuluiMapper;
import com.example.dizertatie.mapper.PacientMapper;
import com.example.dizertatie.repository.FisaPacientuluiRepository;
import com.example.dizertatie.repository.MedicRepository;
import com.example.dizertatie.repository.PacientRepository;
import com.example.dizertatie.repository.ProgramareRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PacientService {

    /*private final ProgramareRepository programareRepository;
    private final PacientMapper pacientMapper;*/

    private static final Integer MINUTES_AVAILABLE_CODE = 5;

    @Autowired
    private PacientRepository pacientRepository;

    @Autowired
    private MedicRepository medicRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private FisaPacientuluiRepository fisaPacientuluiRepository;

    @Autowired
    private ProgramareRepository programareRepository;


    public List<PacientDto> getPacientiCuFisaPentruMedic(Long medicId) {
        // 1. Găsim toate programările la medicul respectiv
        List<Programare> programari = programareRepository.findByMedicId(medicId);

        // 2. Extragem pacienții unici
        Set<Pacient> pacientiUnici = programari.stream()
                .map(Programare::getPacient)
                .collect(Collectors.toSet());

        // 3. Pentru fiecare pacient, construim PacientDto cu fisa completă (dacă există)
        return pacientiUnici.stream().map(pacient -> {
            PacientDto dto = PacientMapper.pacient2Dto(pacient);

            fisaPacientuluiRepository
                    .findByPacientIdAndMedicId(pacient.getId(), medicId)
                    .ifPresent(fisa -> dto.setFisaPacientuluiDto(FisaPacientuluiMapper.fisaPacientuluiEntity2Dto(fisa)));

            return dto;
        }).toList();
    }


    public Pacient savePacient(Pacient pacient) {
        pacient.setParola(codificareParola(pacient.getParola())); // cu codificare
        pacient.setEsteVerificat(false);
        return pacientRepository.save(pacient);
    }

    public boolean existsByCnpOrTelefonOrEmail(String cnp, String telefon, String email) {
        System.out.println(pacientRepository.existsByCnp(cnp));
        System.out.println(pacientRepository.existsByTelefon(telefon));
        System.out.println(pacientRepository.existsByEmail(email));


        return pacientRepository.existsByCnp(cnp) ||
                pacientRepository.existsByTelefon(telefon) ||
                pacientRepository.existsByEmail(email);
    }

    public boolean existsByUtilizator(String utilizator) {

        return pacientRepository.existsByUtilizator(utilizator);

    }

    //------------------------------------------------------------

    public Pacient loginCuEmail(Pacient pacient) {

        return pacientRepository.findByEmail(pacient.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Pacient with email " + pacient.getEmail() + " not found"));
    }

    public Pacient login(Pacient pacientDeAutentificat) {
        Optional<Pacient> pacientGasitOpt = pacientRepository.findByUtilizator(pacientDeAutentificat.getUtilizator());

        if (pacientGasitOpt.isEmpty()) {
            throw new RuntimeException("Utilizator sau parolă incorectă");
        }

        Pacient pacientGasit = pacientGasitOpt.get();

        // criptează parola primită la login
        String parolaCriptata = codificareParola(pacientDeAutentificat.getParola());

        // compară cu parola din BD
        if (!pacientGasit.getParola().equals(parolaCriptata)) {
            throw new RuntimeException("Utilizator sau parolă incorectă");
        }

        /*if (Boolean.FALSE.equals(pacientGasit.getEsteVerificat())) {
            throw new RuntimeException("Contul nu este verificat");
        }*/

        return pacientGasit;
    }

    public void genereazaSiTrimiteCodVerificare(String email) {
        Pacient pacient = pacientRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Pacient cu emailul " + email + " nu exista!"));

        String codVerificare = genereazaCodVerificare();
        pacient.setCodVerificare(codVerificare);
        pacient.setCodVerificareGenerareTimp(LocalDateTime.now());
        pacientRepository.save(pacient);

        emailService.sendVerificationEmail("denisdbl331@gmail.com", codVerificare);

        pacientRepository.save(pacient);
    }

    public boolean verificaCodVerificare(String email, String cod) {
        Pacient pacient = pacientRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Pacient cu emailul " + email + " nu exista!"));

        if (pacient.getCodVerificare() != null &&
                pacient.getCodVerificare().equals(cod) &&
                pacient.getCodVerificareGenerareTimp() != null &&
                pacient.getCodVerificareGenerareTimp().isAfter(LocalDateTime.now().minusMinutes(10))) {

            pacient.setEsteVerificat(true);
            pacient.setCodVerificare(null);
            pacient.setCodVerificareGenerareTimp(null);
            pacientRepository.save(pacient);
            return true;
        }
        return false;
    }
    //------------------------------------------------------------
    /*public Pacient verify(Long pacientId, Pacient updatedPacient) {
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

        if (pacient.getEsteVerificat()) {
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
        if (!existentPacient.getEsteVerificat() || !encodedPassword.equals(existentPacient.getParola())) {
            throw new InputMismatchException();
        }
        return existentPacient;
    }*/

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

    /*public Pacient pacientToCreate(Pacient pacientToCreate) {

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

    }*/

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
        dbPacient.setEsteVerificat(pacientUpdate.getEsteVerificat());


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
        Pacient pacient = pacientRepository.findById(pacientId)
                .orElseThrow(() -> new EntityNotFoundException("Pacientul cu ID " + pacientId + " nu a fost gasit"));
        pacientRepository.delete(pacient);
    }

    public void deleteALL() {
        pacientRepository.deleteAll();
    }

    @Transactional
    public void deleteFisaForPacient(Long pacientId) {
        // Find the Pacient by ID
        Optional<Pacient> optionalPacient = pacientRepository.findById(pacientId);
        if (optionalPacient.isEmpty()) {
            throw new EntityNotFoundException("Pacient with ID " + pacientId + " not found.");
        }

        Pacient pacient = optionalPacient.get();

        // Find and remove the reference to FisaPacientului
        FisaPacientului fisaPacientului = pacient.getFisaPacientului();
        if (fisaPacientului != null) {
            pacient.setFisaPacientului(null);
            pacientRepository.save(pacient);

            // Delete the associated FisaPacientului
            fisaPacientuluiRepository.delete(fisaPacientului);
        }
    }





}
