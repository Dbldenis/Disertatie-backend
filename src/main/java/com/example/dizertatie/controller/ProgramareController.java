package com.example.dizertatie.controller;

import com.example.dizertatie.dto.ProgramareDto;
import com.example.dizertatie.service.ProgramareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}


