package com.example.dizertatie.controller;


import com.example.dizertatie.dto.FisaPacientuluiDto;
import com.example.dizertatie.entities.FisaPacientului;
import com.example.dizertatie.mapper.FisaPacientuluiMapper;
import com.example.dizertatie.service.FisaPacientuluiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/fisa")
public class FisaPacientuluiController {


    @Autowired
    private FisaPacientuluiService fisaPacientuluiService;

    // Creez o fisa pentru un pacient
    @PostMapping("/addFisa/{pacientId}/{medicId}")
    public ResponseEntity<?> addFisaToPacient(@RequestBody FisaPacientuluiDto fisaPacientuluiDto, @PathVariable Long pacientId, @PathVariable Long medicId) {
        FisaPacientului fisaPacientului = FisaPacientuluiMapper.fisaPacientuluiDto2Entity(fisaPacientuluiDto);
        FisaPacientului fisaPacientului1Created = fisaPacientuluiService.creeazaFisa(fisaPacientului, pacientId, medicId);
        FisaPacientuluiDto fisaPacientuluiDto1 = FisaPacientuluiMapper.fisaPacientuluiEntity2Dto(fisaPacientului1Created);

        return ResponseEntity.ok(fisaPacientuluiDto);
    }

    // Actualizează o fișă de pacient
    @PutMapping("/update/{fisaId}")
    public ResponseEntity<FisaPacientului> updateFisa(@RequestBody FisaPacientuluiDto fisaPacientuluiDto, @PathVariable Long fisaId) {
        System.out.println("Am ajuns in update fisa pacientului: " + fisaPacientuluiDto.toString() + " !!!!!!!!!!!!!!!!");
        FisaPacientului fisaActualizata = fisaPacientuluiService.updateFisa(fisaPacientuluiDto, fisaId);
        return ResponseEntity.ok(fisaActualizata);
    }

    // Obține o fișă după id
    @GetMapping("/{fisaId}")
    public ResponseEntity<FisaPacientului> getFisa(@PathVariable Long fisaId) {
        FisaPacientului fisa = fisaPacientuluiService.getFisa(fisaId);
        return ResponseEntity.ok(fisa);
    }

    // Șterge o fșiă după id -- Nu trebuie sa pot sterge fisa pacientului !
    @DeleteMapping("/sterge/{fisaId}")
    public ResponseEntity<?> stergeFisa(@PathVariable Long fisaId) {
        fisaPacientuluiService.deletePacientById(fisaId);
        return ResponseEntity.ok().body(
                Map.of("mesaj", "Fișa pacientului a fost ștearsă cu succes")
        );
    }
}
