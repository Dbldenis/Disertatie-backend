package com.example.dizertatie.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="Programare")
@Table(name="PROGRAMARE",schema = "public")
public class Programare {

    @Id
    @Column
    private Long id;

    @Column(name = "TIP_CONSULTATIE")
    private Consultatie tipConsutlatie;

    @Column(name= "DATA")
    private LocalDate data;

    @Column(name="ORA")
    private LocalTime ora;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    private Pacient pacient;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    private Medic medic;


}
