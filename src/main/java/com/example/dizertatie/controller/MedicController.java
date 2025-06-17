package com.example.dizertatie.controller;

import com.example.dizertatie.dto.*;
import com.example.dizertatie.entities.*;
import com.example.dizertatie.mapper.*;
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



    //Login cu username si parola
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
        System.out.println("Medic Dto :" + medicDto.getEmail());
        try {
            Medic medicLogin = MedicMapper.medic2Entity(medicDto);
            Medic emailMedicGasit = medicService.loginCuEmail(medicLogin);
            return ResponseEntity.ok(MedicMapper.medic2Dto(emailMedicGasit));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email inexistent!");
        }
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

    // Get un singur medic
    @GetMapping("/get/medic/{medicId}")
    public ResponseEntity<MedicDto> getMedic(@PathVariable Long medicId) {
        Medic medic = medicService.gasesteMedicDupaId(medicId); // găsește medicul sau aruncă excepție
        MedicDto dto = MedicMapper.medic2Dto(medic); // mapare entitate -> dto (scrii sau folosești MapStruct)
        return ResponseEntity.ok(dto);
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

    //sterg pacient dupa id
    @DeleteMapping("/delete/{pacientId}")
    public ResponseEntity<?> deletePacientByIdFromMedic(@PathVariable Long pacientId) {
        pacientService.deletePacient(pacientId);
        return ResponseEntity.noContent().build();
    }

    //sterg toti pacienti
    @DeleteMapping("/delete/all/pacienti")
    public ResponseEntity<?> deleteAllPacienti() {
        medicService.deleteAllPacienti();

        return ResponseEntity.ok().build();
    }

}
