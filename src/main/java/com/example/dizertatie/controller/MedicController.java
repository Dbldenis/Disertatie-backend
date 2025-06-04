package com.example.dizertatie.controller;

import com.example.dizertatie.dto.ConsultatieDto;
import com.example.dizertatie.dto.FisaPacientuluiDto;
import com.example.dizertatie.dto.MedicDto;
import com.example.dizertatie.dto.PacientDto;
import com.example.dizertatie.entities.Consultatie;
import com.example.dizertatie.entities.FisaPacientului;
import com.example.dizertatie.entities.Medic;
import com.example.dizertatie.entities.Pacient;
import com.example.dizertatie.mapper.ConsultatiMapper;
import com.example.dizertatie.mapper.FisaPacientuluiMapper;
import com.example.dizertatie.mapper.MedicMapper;
import com.example.dizertatie.mapper.PacientMapper;
import com.example.dizertatie.service.MedicService;
import com.example.dizertatie.service.PacientService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/medic")
public class MedicController {

    @Autowired
    private MedicService medicService;

    @Autowired
    private PacientService pacientService;


    //add medic
    @PostMapping("/add")
    public ResponseEntity<?> createMedic(@Valid @RequestBody MedicDto medicDto) {

        System.out.println("===>>> Am primit un medic in controller: " + medicDto.getNume());
        try {
            // Verifici dacă există deja un medic cu același email sau codParafa
            if (medicService.existsByEmailOrCodParafa(medicDto.getEmail(), medicDto.getCodParafa())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Există deja un medic cu acest email sau cod parafă!");
            }
            Medic medic = MedicMapper.medic2Entity(medicDto);
            Medic medicSalvat = medicService.saveMedic(medic);
            return ResponseEntity.ok(MedicMapper.medic2Dto(medicSalvat));
        } catch (Exception e) {
            //System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Eroare la înregistrarea medicului!");
        }
    }

    /*

        Medic medicCreate = MedicMapper.medic2Entity(medicDto);
        Medic medicCreated = medicService.medicToCreate(medicCreate);

        return ResponseEntity.ok(MedicMapper.medic2Dto(medicCreated));*/



    //Login cu email si parola
    /*@PostMapping("/login")
    public ResponseEntity<?> medicLogin(@RequestBody MedicDto medicDto) {

        Medic medicToLogin = MedicMapper.medic2Entity(medicDto);
        Medic existentMedic = medicService.login(medicToLogin);

        return ResponseEntity.ok(MedicMapper.medic2Dto(existentMedic));
    }*/

    // Trimite codul de verificare pe email
    @PostMapping("/email/send-code")
    public ResponseEntity<?> trimiteCodPeEmail(@RequestBody MedicDto medicDto) {
        try {
            medicService.genereazaSiTrimiteCodVerificare(medicDto.getEmail());
            return ResponseEntity.ok("Codul a fost trimis pe email!");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nu există niciun medic cu acest email!");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Eroare la trimiterea codului!");
        }
    }


    // Verifică codul OTP primit de medic
    @PostMapping("/email/verify-code")
    public ResponseEntity<?> verificaCodOTP(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String cod = payload.get("cod");
        boolean valid = medicService.verificaCodVerificare(email, cod);
        if (valid) {
            return ResponseEntity.ok("Cont verificat și autentificat!");
        } else {
            return ResponseEntity.status(400).body("Cod invalid sau expirat!");
        }
    }

    //Login doar cu email
    @PostMapping("/email/login")
    public ResponseEntity<?> medicLoginEmail(@RequestBody MedicDto medicDto) {
        try {
            Medic medicLogin = MedicMapper.medic2Entity(medicDto);
            Medic emailMedicGasit = medicService.loginCuEmail(medicLogin);
            return ResponseEntity.ok(MedicMapper.medic2Dto(emailMedicGasit));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email inexistent!");
        }
    }


    // crearea consultatie la pacient
    @PostMapping("{medicId}/creare/consultatie/pacient/{pacientId}")
    public ResponseEntity<ConsultatieDto> createConsultatie(@RequestBody ConsultatieDto consultatieDto, @PathVariable Long medicId, @PathVariable Long pacientId) {

        Consultatie consultatie = medicService.createConsultatie(consultatieDto, pacientId);
        ConsultatieDto consultatieDto1 = ConsultatiMapper.consultatie2Dto(consultatie);

        return ResponseEntity.ok(consultatieDto1);
    }

    @PostMapping("/addFisa/{pacientId}/{medicId}")
    public ResponseEntity<?> addFisaToPacient(@RequestBody FisaPacientuluiDto fisaPacientuluiDto, @PathVariable Long pacientId, @PathVariable Long medicId) {
        FisaPacientului fisaPacientului = FisaPacientuluiMapper.fisaPacientuluiDto2Entity(fisaPacientuluiDto);
        FisaPacientului fisaPacientului1Created = medicService.FisaToCreate(fisaPacientului, pacientId, medicId);
        FisaPacientuluiDto fisaPacientuluiDto1 = FisaPacientuluiMapper.fisaPacientuluiEntity2Dto(fisaPacientului1Created);

        return ResponseEntity.ok(fisaPacientuluiDto);
    }

    @PutMapping("/edit/consultatie/{consultatieId}")
    public ResponseEntity<?> editConsultatie(@RequestBody ConsultatieDto consultatieDto, @PathVariable Long consultatieId) {

        Consultatie consultatie2Update = ConsultatiMapper.consultati2Entity(consultatieDto);
        Consultatie consultatieUpdated = medicService.consultatieUpdate(consultatieDto, consultatieId);
        ConsultatieDto consultatieReturn = ConsultatiMapper.consultatie2Dto(consultatieUpdated);

        return ResponseEntity.ok(consultatieReturn);
    }

    @GetMapping("/get/consultatie/{consultatieId}")
    public ResponseEntity<?> getConsultatie(@PathVariable Long consultatieId) {

        Consultatie consultatie = medicService.getConsultatieById(consultatieId);
        return ResponseEntity.ok(consultatie);
    }

    // Get la medici
    @GetMapping("/get/medici")
    public ResponseEntity<List<MedicDto>> getMediciBySpecialitate(@RequestParam String specialitate) {
        List<Medic> mediciFiltrati = medicService.getMediciBySpecialitate(specialitate);

        List<MedicDto> rezultat = mediciFiltrati.stream()
                .map(MedicMapper::medic2Dto )
                .toList();

        System.out.println("Rezultat" + rezultat);
        return ResponseEntity.ok(rezultat);
    }

    @GetMapping("/get/test")
    public ResponseEntity<List<MedicDto>> getMediciBySpecialitate() {
        List<Medic> mediciFiltrati = medicService.findAll();

        List<MedicDto> rezultat = mediciFiltrati.stream()
                .map(MedicMapper::medic2Dto )
                .toList();

        System.out.println("Rezultat" + rezultat);
        return ResponseEntity.ok(rezultat);
    }

    // sterg consultatie dupa id
    @DeleteMapping("/delete/consultatie/{consultatieId}")
    public ResponseEntity<?> deleteConsultatie(@PathVariable Long consultatieId) {
        medicService.deleteConsultatie(consultatieId);
        return ResponseEntity.ok("Consultația a fost ștearsă cu succes.");
    }

    //sterg pacient dupa id
    @DeleteMapping("/delete/{pacientId}")
    public ResponseEntity<?> deletePacientByIdFromMedic(@PathVariable Long pacientId) {
        pacientService.deletePacient(pacientId);
        return ResponseEntity.noContent().build();
    }

    //sterg toate consultatile
    @DeleteMapping("/delete/all/consultati")
    public ResponseEntity<?> deleteAllConsultati() {
        medicService.deleteAllConsultati();

        return ResponseEntity.ok("Toate Consultațiile au fost șterse cu succes.");
    }

    //sterg toti pacienti
    @DeleteMapping("/delete/all/pacienti")
    public ResponseEntity<?> deleteAllPacienti() {
        medicService.deleteAllPacienti();

        return ResponseEntity.ok().build();
    }


    // GPT








}
