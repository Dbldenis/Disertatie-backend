package com.example.dizertatie.controller;

import com.example.dizertatie.dto.PacientDto;
import com.example.dizertatie.entities.Pacient;
import com.example.dizertatie.mapper.PacientMapper;
import com.example.dizertatie.service.MedicService;
import com.example.dizertatie.service.PacientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacient")
public class PacientController {

    @Autowired
    private PacientService pacientService;

    @Autowired
    private MedicService medicService;

    @Autowired
    private MedicController medicController;

    // add pacient
    @PostMapping("/add/{medicId}")
    public ResponseEntity<?> addPacientToMedic(@RequestBody PacientDto pacientDto, @PathVariable Long medicId) {
        Pacient pacientCreate = PacientMapper.pacient2Entity(pacientDto);
        Pacient pacientCreated = pacientService.pacientToCreate(pacientCreate,medicId);

        PacientDto pacientDto1 = PacientMapper.pacient2Dto(pacientCreated);

        return ResponseEntity.ok(pacientDto1);
    }

    // edit pacient
    @PutMapping("/edit/{pacientId}")
    public ResponseEntity<?> editPacient(@RequestBody PacientDto pacientDto, @PathVariable Long pacientId) {

        Pacient pacientToUpdate = PacientMapper.pacient2Entity(pacientDto);
        Pacient pacientUpdated = pacientService.pacientUpdate(pacientToUpdate,pacientId);
        PacientDto pacientDto1 = PacientMapper.pacient2Dto(pacientUpdated);

        return ResponseEntity.ok(pacientDto1);
    }

    // get pacient by id
    @GetMapping("/get/{pacientId}")
    public ResponseEntity<?> getPacientById(@PathVariable(name = "pacientId") Long pacientToGet) {
        Pacient pacientFound = pacientService.getPacientData(pacientToGet);
        PacientDto pacientData = PacientMapper.pacient2Dto(pacientFound);

        return ResponseEntity.ok(pacientData);
    }





}
