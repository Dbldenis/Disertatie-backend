package com.example.dizertatie.controller;

import com.example.dizertatie.dto.FisaPacientuluiDto;
import com.example.dizertatie.dto.MedicDto;
import com.example.dizertatie.entities.FisaPacientului;
import com.example.dizertatie.entities.Medic;
import com.example.dizertatie.mapper.FisaPacientuluiMapper;
import com.example.dizertatie.mapper.MedicMapper;
import com.example.dizertatie.service.MedicService;
import com.example.dizertatie.service.PacientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medic")
public class MedicController {

    @Autowired
    private MedicService medicService;

    @Autowired
    private PacientService pacientService;

    //add medic
    @PostMapping("/add")
    public ResponseEntity<?> createMedic(@RequestBody MedicDto medicDto) {

        Medic medicToCreate = MedicMapper.medic2Entity(medicDto);
        Medic medicCreated = medicService.medicToCreate(medicToCreate);

        return ResponseEntity.ok(medicCreated);
    }

    // add fisa pacientului
    @PostMapping("/addFisa/{pacientId}/{medicId}")
    public ResponseEntity<?> addFisaToPacient(@RequestBody FisaPacientuluiDto fisaPacientuluiDto, @PathVariable Long pacientId, @PathVariable Long medicId) {
        FisaPacientului fisaPacientului = FisaPacientuluiMapper.fisaPacientuluiDto2Entity(fisaPacientuluiDto);
        FisaPacientului fisaPacientului1Created = medicService.FisaToCreate(fisaPacientului,pacientId, medicId);
        FisaPacientuluiDto fisaPacientuluiDto1 = FisaPacientuluiMapper.fisaPacientuluiEntity2Dto(fisaPacientului1Created);

        return ResponseEntity.ok(fisaPacientuluiDto);
    }

    //sterg pacient dupa id
    @DeleteMapping("/delete/{pacientId}")
    public ResponseEntity<?> deletePacientByIdFromMedic(@PathVariable Long pacientId) {
        pacientService.deletePacient(pacientId);
        return ResponseEntity.noContent().build();
    }

    //sterg toti pacienti
    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAll() {
        medicService.deleteAllPacienti();

        return ResponseEntity.ok().build();
    }

}
