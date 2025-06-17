package com.example.dizertatie.controller;

import com.example.dizertatie.dto.ProgramareDto;
import com.example.dizertatie.mapper.ProgramareMapper;
import com.example.dizertatie.service.ProgramareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.dizertatie.entities.Programare;

import java.util.List;

@RestController
@RequestMapping("/programari")
public class ProgramareController {

    @Autowired
    private ProgramareService programareService;

    @GetMapping
    public ResponseEntity<List<ProgramareDto>> getProgramari(@RequestParam("pacientId") Long pacientId) {
        List<ProgramareDto> programari = programareService.getProgramariInfoPentruPacient(pacientId);
        return ResponseEntity.ok(programari);
    }

    // Filtrare dupa medic id
    @GetMapping("/get/programari/{medicId}")
    public ResponseEntity<List<ProgramareDto>> getProgramariMedic(@PathVariable Long medicId) {
        List<Programare> programari = programareService.getProgramariPentruMedic(medicId);

        List<ProgramareDto> raspuns = programari.stream()
                .map(ProgramareMapper::exemplarToDTO)
                .toList();

        return ResponseEntity.ok(raspuns);
    }

    // Trebuie get programari in loc de pacient id trebuie un medic id --- asta pentru programarile unui medic
    // La fel ca sus

}


