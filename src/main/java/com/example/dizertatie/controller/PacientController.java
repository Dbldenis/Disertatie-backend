package com.example.dizertatie.controller;

import com.example.dizertatie.dto.MedicDto;
import com.example.dizertatie.entities.Medic;
import com.example.dizertatie.mapper.MedicMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

@CrossOrigin(origins = "http://localhost:8081")
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

    // add pacient - WORKS
    /*@PostMapping("/add/{medicId}")
    public ResponseEntity<?> addPacientToMedic(@Validated(ValidationOrder.class) @RequestBody PacientDto pacientDto) {
        Pacient pacientCreate = PacientMapper.pacient2Entity(pacientDto);
        Pacient pacientCreated = pacientService.pacientToCreate(pacientCreate);

        return ResponseEntity.ok(PacientMapper.pacient2Dto(pacientCreated));
    }*/

    /*@PostMapping("/add")
    public ResponseEntity<?> addPacient(@RequestBody PacientDto pacientDto) {

        Pacient pacientCreate = PacientMapper.pacient2Entity(pacientDto);
        Pacient pacientCreated = pacientService.pacientToCreate(pacientCreate);

        return ResponseEntity.ok(PacientMapper.pacient2Dto(pacientCreated));
    }*/

    @PostMapping("/create")
    public ResponseEntity<?> createPacient(@Valid @RequestBody PacientDto pacientDto) {
        System.out.println("Am ajuns in create pacient" + pacientDto.toString());
        try {
            if (pacientService.existsByUtilizator(pacientDto.getUtilizator())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Există deja un pacient cu acest nume de utilizator!");
            }
            Pacient pacient = PacientMapper.pacient2Entity(pacientDto);
            Pacient pacientSalvat = pacientService.savePacient(pacient);
            return ResponseEntity.ok(PacientMapper.pacient2Dto(pacientSalvat));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Eroare la înregistrarea pacientului!");
        }
    }

    // -----------------------------------

    // Trimite codul de verificare pe email
    @PostMapping("/email/send-code")
    public ResponseEntity<?> trimiteCodPeEmail(@RequestBody PacientDto pacientDto) {
        try {
            pacientService.genereazaSiTrimiteCodVerificare(pacientDto.getEmail());
            return ResponseEntity.ok("Codul a fost trimis pe email!");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nu exista niciun pacient cu acest email!");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Eroare la trimiterea codului!");
        }
    }

    // Verifica codul OTP primit de pacient
    @PostMapping("/email/verify-code")
    public ResponseEntity<?> verificaCodOTP(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String cod = payload.get("cod");
        boolean valid = pacientService.verificaCodVerificare(email, cod);
        if (valid) {
            return ResponseEntity.ok("Cont verificat si autentificat!");
        } else {
            return ResponseEntity.status(400).body("Cod invalid sau expirat!");
        }
    }

    // Login doar cu email
    @PostMapping("/email/login")
    public ResponseEntity<?> pacientLoginEmail(@RequestBody PacientDto pacientDto) {
        try {
            Pacient pacientLogin = PacientMapper.pacient2Entity(pacientDto);
            Pacient pacientGasit = pacientService.loginCuEmail(pacientLogin);
            return ResponseEntity.ok(PacientMapper.pacient2Dto(pacientGasit));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email inexistent!");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> pacientLogin(@RequestBody PacientDto pacientDto) {

        Pacient pacientToLogin = PacientMapper.pacient2Entity(pacientDto);
        Pacient existentPacient = pacientService.login(pacientToLogin);

        return ResponseEntity.ok(PacientMapper.pacient2Dto(existentPacient));
    }

    //------------------------------------
    /*@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody PacientDto pacientDto) {
        Pacient pacientToLogin = PacientMapper.pacient2Entity(pacientDto);
        Pacient existentPacient = pacientService.login(pacientToLogin);

        return ResponseEntity.ok(PacientMapper.pacient2Dto(existentPacient));
    }*/

    //add programare
    @PostMapping("/add/programare/{pacientId}/{medicId}")
    public ResponseEntity<ProgramareDto> creazaProgramare(@RequestBody ProgramareDto programareDto, @PathVariable Long pacientId, @PathVariable Long medicId) {
        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(programareDto.toString());
        Programare programareNoua = ProgramareMapper.programare2Entity(programareDto);
        Programare programare = programareService.creazaProgramare(programareNoua, pacientId, medicId);
        ProgramareDto programareDto1 = ProgramareMapper.exemplarToDTO(programare);

        return ResponseEntity.ok(programareDto1);
    }

    //add fisa pacientului
    @PostMapping("/add/fisa/{pacientId}/{medicId}")
    public ResponseEntity<FisaPacientuluiDto> adaugaFisaPacientului(@RequestBody FisaPacientuluiDto fisaPacientuluiDto, @PathVariable Long pacientId, @PathVariable Long medicId) {
        System.out.println("Creez fisa !?!!!!!!!!!!!!!!1");
        FisaPacientului fisa = FisaPacientuluiMapper.fisaPacientuluiDto2Entity(fisaPacientuluiDto);
        FisaPacientului fisaCreata = fisaPacientuluiService.creeazaFisa(fisa, pacientId, medicId);
        FisaPacientuluiDto fisaPacientuluiDto1 = FisaPacientuluiMapper.fisaPacientuluiEntity2Dto(fisaCreata);

        return ResponseEntity.ok(fisaPacientuluiDto1);
    }


    /*@PutMapping("/{pacientId}")
    public ResponseEntity<?> verify(@PathVariable Long pacientId, @RequestBody PacientDto pacientDto) {
        Pacient pacientToUpdate = PacientMapper.pacient2Entity(pacientDto);
        Pacient updatedPacient = pacientService.verify(pacientId, pacientToUpdate);

        return ResponseEntity.ok(PacientMapper.pacient2Dto(updatedPacient));
    }

    // Retrimit codul - WORKS
    @PutMapping("/retrimiteCod/{pacientId}")
    public ResponseEntity<?> retrimitereCodVerificare(@PathVariable Long pacientId) {
        Pacient pacient = pacientService.retrimiteCodVerificare(pacientId);

        return ResponseEntity.ok(PacientMapper.pacient2Dto(pacient));
    }*/


    //edit fisa pacientului
    @PutMapping(value = "/edit/fisa/{fisaId}", consumes = MediaType.APPLICATION_JSON_VALUE) // pentru Json
    public ResponseEntity<FisaPacientuluiDto> updateFisaInit(@PathVariable Long fisaId, @RequestBody FisaPacientuluiDto fisaPacientuluiDto) {
        FisaPacientului fisaPacientului = FisaPacientuluiMapper.fisaPacientuluiDto2Entity(fisaPacientuluiDto);
        FisaPacientului fisaCreata = fisaPacientuluiService.updateFisa(fisaPacientuluiDto, fisaId);
        FisaPacientuluiDto fisaPacientuluiDto1 = FisaPacientuluiMapper.fisaPacientuluiEntity2Dto(fisaCreata);
        return ResponseEntity.ok(fisaPacientuluiDto1);
    }

    @PutMapping(value = "/edit/fisa", consumes = MediaType.APPLICATION_JSON_VALUE) // pentru Json
    public ResponseEntity<FisaPacientuluiDto> updateFisa( @RequestBody FisaPacientuluiDto fisaPacientuluiDto) {
        Long fisaId = fisaPacientuluiDto.getId();
        FisaPacientului fisaPacientului = FisaPacientuluiMapper.fisaPacientuluiDto2Entity(fisaPacientuluiDto);
        FisaPacientului fisaCreata = fisaPacientuluiService.updateFisa(fisaPacientuluiDto, fisaId);
        FisaPacientuluiDto fisaPacientuluiDto1 = FisaPacientuluiMapper.fisaPacientuluiEntity2Dto(fisaCreata);
        return ResponseEntity.ok(fisaPacientuluiDto1);
    }

    // edit programare
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






    /*@DeleteMapping("/delete/fisa/{fisaId}")
    public ResponseEntity<?> stergeFisaPacientului(@PathVariable Long fisaId) {

        fisaPacientuluiService.stergeFisaDupaId(fisaId);

        return ResponseEntity.ok().body(
                Map.of("mesaj", "Fisa pacientului a fost ștearsă cu succes")
        );

    }*/

    //TEST
    /*@PostMapping(value = "/test", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test(@RequestBody Map<String, Object> payload) {
        return ResponseEntity.ok("OK");
    }*/

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





    // Trebuie get programari in loc de pacient id trebuie un medic id --- asta pentru programarile unui medic
    // La fel ca sus

    //sterge programarea
    @DeleteMapping("/delete/programare/{programareId}")
    public ResponseEntity<?> stergeProgramare(@PathVariable Long programareId) {
        programareService.stergeProgramare(programareId);

        return ResponseEntity.ok().body(
                Map.of("mesaj", "Programarea a fost ștearsă cu succes")
        );
    }

    // Implementeaza optiunea de a sterge toate programarile

    @DeleteMapping("/delete/toate-programarile")
    public ResponseEntity<?> stergeToateProgramarile() {
        programareService.stergeToateProgramarile();

        return ResponseEntity.ok().body(
                Map.of("mesaj", "Toate programările au fost șterse cu succes")
        );
    }



}
