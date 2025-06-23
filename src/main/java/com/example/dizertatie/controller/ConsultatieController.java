package com.example.dizertatie.controller;

import com.example.dizertatie.dto.ConsultatieDto;
import com.example.dizertatie.entities.Consultatie;
import com.example.dizertatie.mapper.ConsultatiMapper;
import com.example.dizertatie.service.ConsultatieService;
import com.example.dizertatie.service.MedicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultatie")
public class ConsultatieController {

    @Autowired
    private ConsultatieService consultatieService;

    @Autowired
    private MedicService medicService;


    // POST consultatie la pacient
    @PostMapping("{medicId}/creare/consultatie/pacient/{pacientId}")
    public ResponseEntity<ConsultatieDto> createConsultatie(@RequestBody ConsultatieDto consultatieDto, @PathVariable Long medicId, @PathVariable Long pacientId) {

        System.out.println("Am ajuns in create consultatie: " + consultatieDto.toString() + " !!!!!!!!!!!!!!!!");
        System.out.println("Am ajuns in create consultatie medicId: " + medicId + " !!!!!!!!!!!!!!!!");
        System.out.println("Am ajuns in create consultatie pacientId: " + pacientId + " !!!!!!!!!!!!!!!!");
        consultatieDto.setMedicId(medicId);
        consultatieDto.setPacientId(pacientId);
        Consultatie consultatie = consultatieService.createConsultatie(consultatieDto, pacientId);
        ConsultatieDto consultatieDto1 = ConsultatiMapper.consultatie2Dto(consultatie);

        return ResponseEntity.ok(consultatieDto1);
    }

    // PUT consultatie
    @PutMapping("/edit/consultatie/{consultatieId}")
    public ResponseEntity<?> editConsultatie(@RequestBody ConsultatieDto consultatieDto, @PathVariable Long consultatieId) {

        Consultatie consultatie2Update = ConsultatiMapper.consultati2Entity(consultatieDto);
        Consultatie consultatieUpdated = consultatieService.consultatieUpdate(consultatieDto, consultatieId);
        ConsultatieDto consultatieReturn = ConsultatiMapper.consultatie2Dto(consultatieUpdated);

        return ResponseEntity.ok(consultatieReturn);
    }

    // GET consultatie
    @GetMapping("/get/consultatie/{consultatieId}")
    public ResponseEntity<?> getConsultatie(@PathVariable Long consultatieId) {

        Consultatie consultatie = consultatieService.getConsultatieById(consultatieId);
        return ResponseEntity.ok(consultatie);
    }

    @GetMapping("/get/consultatii/{medicId}/{pacientId}")
    public ResponseEntity<?> getConsultatiiByMedicAndPacient(
            @PathVariable Long medicId,
            @PathVariable Long pacientId) {

        List<Consultatie> consultatii = consultatieService
                .getConsultatiiByMedicIdAndPacientId(medicId, pacientId);

        return ResponseEntity.ok(consultatii);
    }


    // sterg consultatie dupa id
    @DeleteMapping("/delete/consultatie/{consultatieId}")
    public ResponseEntity<?> deleteConsultatie(@PathVariable Long consultatieId) {
        consultatieService.deleteConsultatie(consultatieId);
        return ResponseEntity.ok("Consultația a fost ștearsă cu succes.");
    }

    //sterg toate consultatile
    @DeleteMapping("/delete/all/consultati")
    public ResponseEntity<?> deleteAllConsultati() {
        consultatieService.deleteAllConsultati();

        return ResponseEntity.ok("Toate Consultațiile au fost șterse cu succes.");
    }


    // Sa imi genereze controllerul,service si repository
    // Sa faca maparea cu consultatie Dto cum este la pacient controller
    // Tot flow-ul
    // Endpoint-urile necesare cu frontend. (creaza consultatie(dto), editare(id), stergere(id), sterge all, get(id) )
    // si vezi toate consultatile unui pacient(id pacient)


    // Ce fac la consultatie sa fac si la fisa pacient.
    // Create, edit (id), get(id fisa), get(id pacient)





}
