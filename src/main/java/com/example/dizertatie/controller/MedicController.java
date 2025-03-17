package com.example.dizertatie.controller;


import com.example.dizertatie.dto.MedicDto;
import com.example.dizertatie.dto.PacientDto;
import com.example.dizertatie.entities.Medic;
import com.example.dizertatie.entities.Pacient;
import com.example.dizertatie.mapper.MedicMapper;
import com.example.dizertatie.mapper.PacientMapper;
import com.example.dizertatie.service.MedicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medic")
public class MedicController {

    @Autowired
    private MedicService medicService;

    //add medic
    @PostMapping("/add")
    public ResponseEntity<?> createMedic(@RequestBody MedicDto medicDto) {

        Medic medicToCreate = MedicMapper.medic2Entity(medicDto);
        Medic medicCreated = medicService.medicToCreate(medicToCreate);

        return ResponseEntity.ok(medicCreated);
    }

}
