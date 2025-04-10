package com.example.dizertatie.controller;

import org.springframework.http.MediaType;
import com.example.dizertatie.dto.FisaPacientuluiDto;
import com.example.dizertatie.dto.PacientDto;
import com.example.dizertatie.dto.ProgramareDto;
import com.example.dizertatie.entities.FisaPacientului;
import com.example.dizertatie.entities.Pacient;
import com.example.dizertatie.entities.Programare;
import com.example.dizertatie.mapper.PacientMapper;
import com.example.dizertatie.mapper.ProgramareMapper;
import com.example.dizertatie.service.FisaPacientuluiService;
import com.example.dizertatie.service.MedicService;
import com.example.dizertatie.service.PacientService;
import com.example.dizertatie.service.ProgramareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.dizertatie.mapper.FisaPacientuluiMapper;

import java.util.List;
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

    @Autowired
    private FisaPacientuluiService fisaPacientuluiService;






    //REGISTER
    /*@PostMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam String email, @RequestParam String code) {
        pacientService.verify(email, code);
        return ResponseEntity.noContent().build();
    }*/

    // add pacient
    @PostMapping("/add/{medicId}")
    public ResponseEntity<?> addPacientToMedic(@RequestBody PacientDto pacientDto, @PathVariable Long medicId) {
        Pacient pacientCreate = PacientMapper.pacient2Entity(pacientDto);
        Pacient pacientCreated = pacientService.pacientToCreate(pacientCreate, medicId);

        PacientDto pacientDto1 = PacientMapper.pacient2Dto(pacientCreated);

        return ResponseEntity.ok(pacientDto1);
    }

    //########### # # # # # #
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody PacientDto pacientDto) {
        Pacient pacientToLogin = PacientMapper.pacient2Entity(pacientDto);
        Pacient existentPacient = pacientService.login(pacientToLogin);
        PacientDto pacientDto1 = PacientMapper.pacient2Dto(existentPacient);

        return ResponseEntity.ok(pacientDto1);
    }



    //add programare
    @PostMapping("/add/programare/{pacientId}/{medicId}")
    public ResponseEntity<ProgramareDto> creazaProgramare(@RequestBody ProgramareDto programareDto, @PathVariable Long pacientId, @PathVariable Long medicId) {
        Programare programareNoua = ProgramareMapper.programare2Entity(programareDto);
        Programare programare = programareService.creazaProgramare(programareNoua, pacientId, medicId);
        ProgramareDto programareDto1 = ProgramareMapper.exemplarToDTO(programare);

        return ResponseEntity.ok(programareDto1);
    }

    //add fisa pacientului
    @PostMapping(value = "/add/fisa/{pacientId}/{medicId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FisaPacientuluiDto> adaugaFisaPacientului(@RequestBody FisaPacientuluiDto fisaPacientuluiDto, @PathVariable Long pacientId, @PathVariable Long medicId) {
        FisaPacientului fisa = FisaPacientuluiMapper.fisaPacientuluiDto2Entity(fisaPacientuluiDto);
        FisaPacientului fisaCreata = fisaPacientuluiService.creeazaFisa(fisa, pacientId, medicId);
        FisaPacientuluiDto fisaPacientuluiDto1 = FisaPacientuluiMapper.fisaPacientuluiEntity2Dto(fisaCreata);

        return ResponseEntity.ok(fisaPacientuluiDto1);
    }

    //edit fisa pacientului
    @PutMapping(value = "/edit/fisa/{fisaId}", consumes = MediaType.APPLICATION_JSON_VALUE) // pentru Json
    public ResponseEntity<FisaPacientuluiDto> updateFisa(@PathVariable Long fisaId, @RequestBody FisaPacientuluiDto fisaPacientuluiDto) {
        FisaPacientului fisaPacientului = FisaPacientuluiMapper.fisaPacientuluiDto2Entity(fisaPacientuluiDto);
        FisaPacientului fisaCreata = fisaPacientuluiService.updateFisa(fisaPacientului, fisaId);
        FisaPacientuluiDto fisaPacientuluiDto1 = FisaPacientuluiMapper.fisaPacientuluiEntity2Dto(fisaCreata);
        return ResponseEntity.ok(fisaPacientuluiDto1);
    }

    //get fisa pacientului
    @GetMapping("/get/fisa/{fisaId}")
    public ResponseEntity<FisaPacientuluiDto> getFisa(@PathVariable Long fisaId) {

        FisaPacientului fisaPacientuluiGasita = fisaPacientuluiService.getFisa(fisaId);
        FisaPacientuluiDto fisaPacientuluiDto = FisaPacientuluiMapper.fisaPacientuluiEntity2Dto(fisaPacientuluiGasita);

        return ResponseEntity.ok(fisaPacientuluiDto);
    }

    // get pacient by id
    @GetMapping("/get/{pacientId}")
    public ResponseEntity<?> getPacientById(@PathVariable(name = "pacientId") Long pacientToGet) {
        Pacient pacientFound = pacientService.getPacientData(pacientToGet);
        PacientDto pacientData = PacientMapper.pacient2Dto(pacientFound);

        return ResponseEntity.ok(pacientData);
    }


    @DeleteMapping("/delete/fisa/{fisaId}")
    public ResponseEntity<?> stergeFisaPacientului(@PathVariable Long fisaId) {

        fisaPacientuluiService.stergeFisaDupaId(fisaId);

        return ResponseEntity.ok().body(
                Map.of("mesaj", "Fisa pacientului a fost ștearsă cu succes")
        );

    }

    //TEST
    @PostMapping(value = "/test", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test(@RequestBody Map<String, Object> payload) {
        return ResponseEntity.ok("OK");
    }

    //edit programre
    @PutMapping("/edit/programare/{programareId}")
    public ResponseEntity<ProgramareDto> editeazaProgramare(@PathVariable Long programareId, @RequestBody ProgramareDto dto) {

        Programare programareActualizata = programareService.editeazaProgramare(programareId, dto);
        ProgramareDto raspuns = ProgramareMapper.exemplarToDTO(programareActualizata);

        return ResponseEntity.ok(raspuns);
    }

    // edit pacient
    @PutMapping("/edit/{pacientId}")
    public ResponseEntity<?> editPacient(@RequestBody PacientDto pacientDto, @PathVariable Long pacientId) {

        Pacient pacientToUpdate = PacientMapper.pacient2Entity(pacientDto);
        Pacient pacientUpdated = pacientService.pacientUpdate(pacientToUpdate, pacientId);
        PacientDto pacientDto1 = PacientMapper.pacient2Dto(pacientUpdated);

        return ResponseEntity.ok(pacientDto1);
    }

    //get programare
    @GetMapping("/get/programare/{programareId}")
    public ResponseEntity<ProgramareDto> getProgramare(@PathVariable Long programareId) {
        Programare programare = programareService.getProgramareById(programareId);
        ProgramareDto dto = ProgramareMapper.exemplarToDTO(programare);
        return ResponseEntity.ok(dto);
    }

    // Filtrare dupa pacient Id
    @GetMapping("/get/programari/{pacientId}")
    public ResponseEntity<List<ProgramareDto>> getProgramariPacient(@PathVariable Long pacientId) {
        List<Programare> programari = programareService.getProgramariPentruPacient(pacientId);

        List<ProgramareDto> raspuns = programari.stream()
                .map(ProgramareMapper::exemplarToDTO)
                .toList();

        return ResponseEntity.ok(raspuns);
    }

    //sterge programarea
    @DeleteMapping("/delete/programare/{programareId}")
    public ResponseEntity<?> stergeProgramare(@PathVariable Long programareId) {
        programareService.stergeProgramare(programareId);

        return ResponseEntity.ok().body(
                Map.of("mesaj", "Programarea a fost ștearsă cu succes")
        );

    }

}
