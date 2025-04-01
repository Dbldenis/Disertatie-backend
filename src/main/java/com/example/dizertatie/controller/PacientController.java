package com.example.dizertatie.controller;

import com.example.dizertatie.dto.PacientDto;
import com.example.dizertatie.dto.ProgramareDto;
import com.example.dizertatie.entities.Pacient;
import com.example.dizertatie.entities.Programare;
import com.example.dizertatie.mapper.PacientMapper;
import com.example.dizertatie.mapper.ProgramareMapper;
import com.example.dizertatie.service.MedicService;
import com.example.dizertatie.service.PacientService;
import com.example.dizertatie.service.ProgramareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/pacient")
public class PacientController {

    @Autowired
    private PacientService pacientService;

    @Autowired
    private MedicService medicService;

    @Autowired
    private MedicController medicController;

    @Autowired
    private ProgramareService programareService;

    // add pacient
    @PostMapping("/add/{medicId}")
    public ResponseEntity<?> addPacientToMedic(@RequestBody PacientDto pacientDto, @PathVariable Long medicId) {
        Pacient pacientCreate = PacientMapper.pacient2Entity(pacientDto);
        Pacient pacientCreated = pacientService.pacientToCreate(pacientCreate,medicId);

        PacientDto pacientDto1 = PacientMapper.pacient2Dto(pacientCreated);

        return ResponseEntity.ok(pacientDto1);
    }

    //add programare
    @PostMapping("/add/programare/{pacientId}/{medicId}")
    public ResponseEntity<ProgramareDto> creazaProgramare(@RequestBody ProgramareDto programareDto, @PathVariable Long pacientId, @PathVariable Long medicId ) {
        Programare programareNoua = ProgramareMapper.programare2Entity(programareDto);
        Programare programare = programareService.creazaProgramare(programareNoua, pacientId, medicId);
        ProgramareDto programareDto1 = ProgramareMapper.exemplarToDTO(programare);

        return ResponseEntity.ok(programareDto1);
    }

    //edit programre


    //sterge programarea
    @DeleteMapping("/delete/programare/{programareId}")
    public ResponseEntity<?> stergeProgramare(@PathVariable Long programareId) {
        programareService.stergeProgramare(programareId);

        return ResponseEntity.ok().body(
                Map.of("mesaj", "Programarea a fost ștearsă cu succes")
        );

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

    // delete all pacienti
    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAll() {
        pacientService.deleteALL();

        return ResponseEntity.ok().build();
    }

    // Creez programare
    /*@PostMapping("/programare/{medicId}")
    public ResponseEntity<?> createProgramare(@PathVariable(name = "pacientId")){

    }*/






}
