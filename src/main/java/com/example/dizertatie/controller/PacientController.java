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
        Pacient pacientToCreate = PacientMapper.pacient2Entity(pacientDto);
        Pacient pacientCreated = pacientService.pacientToCreate(pacientToCreate,medicId);

        return ResponseEntity.ok(pacientCreated);
    }


}
