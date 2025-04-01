package com.example.dizertatie.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Programare")
@Table(name = "PROGRAMARE", schema = "public")
public class Programare {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIP_CONSULTATIE")
    private Consultatie tipConsutlatie;

    @Column(name = "DATA")
    private LocalDate data;

    @Column(name = "ORA")
    private LocalTime ora;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            orphanRemoval = true)
    private Pacient pacient;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            orphanRemoval = true)
    private Medic medic;

    // tip consultatie, data ora

}
