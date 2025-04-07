package com.example.dizertatie.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Consultatie")
@Table(name = "CONSULTATIE", schema = "public")
public class Consultatie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATA")
    private LocalDateTime dataConsultatiei;

    @Column(name = "OBSERVATI")
    private String observati;

    @Column(name = "SIMPTOME")
    private String simptome;

    @Column(name = "TRATAMENT")
    private String tratament;

    @Column(name = "DIAGNOSTIC")
    private String diagnostic;

    /*@Column(name = "TIP_CONSULTATIE")
    private TipConsultatie tipConsultatie;*/

    @ManyToOne()
    @JoinColumn(name = "fisa_id")
    private FisaPacientului fisaPacientului;

}

