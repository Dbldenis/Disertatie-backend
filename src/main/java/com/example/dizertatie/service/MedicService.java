package com.example.dizertatie.service;

import com.example.dizertatie.dto.MedicDto;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;

@Service
public class MedicService {

    @Autowired
    private EmailService emailService;

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

    public Medic loginCuEmail(Medic medic) {

        return medicRepository.findByEmail(medic.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Medic with email " + medic.getEmail() + " not found"));
    }

    public Medic saveMedic(Medic medic) {
        // Aici poți face și validări suplimentare, ex: lungime parolă, format email, etc.


        medic.setParola(medic.getParola());
        medic.setEsteVerificat(false); // sau true, dacă e deja verificat cu OTP
        return medicRepository.save(medic);
    }

    public boolean existsByEmailOrCodParafa(String email, Long codParafa) {
        return medicRepository.existsByEmail(email) || medicRepository.existsByCodParafa(codParafa);
    }


    /*public Medic login(Medic medic) {

        Medic existentMedic = medicRepository.findByEmail(medic.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Medic with email " + medic.getEmail() + " not found"));

        String encodedPassword = codificareParola(medic.getParola());
        if (!existentMedic.isEsteVerificat() || !encodedPassword.equals(existentMedic.getParola())) {
            throw new InputMismatchException();
        }
        return existentMedic;
    }*/

    private String genereazaCodVerificare() {
        Random random = new Random();
        int codul = 100000 + random.nextInt(900000); // Generate 6-digit code
        return String.valueOf(codul);
    }

    // Generează și trimite codul pe email
    public void genereazaSiTrimiteCodVerificare(String email) {
        Medic medic = medicRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Medic cu emailul " + email + " nu există!"));

        System.out.println("Trimit cod către: " + email);
        String codVerificare = genereazaCodVerificare();
        medic.setCodVerificare(codVerificare);
        medic.setCodVerificareGenerareTimp(LocalDateTime.now());
        medicRepository.save(medic);

        // Folosește serviciul tău existent
        emailService.sendVerificationEmail(medic.getEmail(), codVerificare);

        medicRepository.save(medic);

    }

    // Verifică codul OTP
    public boolean verificaCodVerificare(String email, String cod) {
        Medic medic = medicRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Medic cu emailul " + email + " nu există!"));

        // Verifică codul și dacă nu a expirat (ex: valabil 10 min)
        if (medic.getCodVerificare() != null &&
                medic.getCodVerificare().equals(cod) &&
                medic.getCodVerificareGenerareTimp() != null &&
                medic.getCodVerificareGenerareTimp().isAfter(LocalDateTime.now().minusMinutes(10))) {

            medic.setEsteVerificat(true);
            medic.setCodVerificare(null);
            medic.setCodVerificareGenerareTimp(null);
            medicRepository.save(medic);
            return true;
        }
        return false;
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

    public Medic CreareMedicSiEmail(Medic medicToCreate) {

        if (medicToCreate.getId() != null) {
            throw new RuntimeException("You cannot provide an ID to a new medic that you want to create");
        }

        if (medicToCreate.getCodVerificare() != null) {
            throw new RuntimeException("You cannot provide a verification code to a pacient");
        }

        medicToCreate.getClinica().addMedic(medicToCreate);

        // aici era apelata criptarea parolei
        medicToCreate.setParola(medicToCreate.getParola());
        String verificationCode = genereazaCodVerificare();
        medicToCreate.setCodVerificare(verificationCode);

        emailService.sendVerificationEmail(medicToCreate.getEmail(), verificationCode);
        medicToCreate.setCodVerificareGenerareTimp(LocalDateTime.now());

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


        /*Medic medic = medicRepository.findById(medicId).
                orElseThrow(EntityNotFoundException::new);

        fisaPacientuluiToCreate.setMedic(medic);*/


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

        fisa.getListaConsultati().add(consultatie); // Dacă relația este implementată

        pacientRepository.save(pacient);

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

    public List<Medic> getMediciBySpecialitate(String specialitate) {
        return medicRepository.findBySpecializareIgnoreCase(specialitate);
    }

    public void deleteConsultatie(Long consultatieId) {
        consultatiRepository.deleteById(consultatieId);
    }

    public void deleteAllConsultati() {
        consultatiRepository.deleteAll();
    }

    public void deleteAllPacienti() {
        medicRepository.deleteAll();
    }

    public List<Medic> findAll() {
        return medicRepository.findAll();
    }
}
