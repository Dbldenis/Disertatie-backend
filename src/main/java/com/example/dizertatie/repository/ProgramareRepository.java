package com.example.dizertatie.repository;

import com.example.dizertatie.entities.Programare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProgramareRepository extends JpaRepository<Programare, Long> {

    //List<Programare> findAllByPacientId(Long pacientId);

    @Query("SELECT DISTINCT p FROM Programare p WHERE p.pacient.id = :pacientId")
    List<Programare> findAllByPacientId(@Param("pacientId") Long pacientId);

    //@Query("SELECT DISTINCT p FROM Programare p WHERE p.medic.id = :medicId")
    @Query("SELECT p FROM Programare p " +
            "JOIN FETCH p.pacient " +
            "JOIN FETCH p.medic " +
            "WHERE p.medic.id = :medicId")
    List<Programare> findAllByMedicId(@Param("medicId") Long medicId);

    List<Programare> findByMedicId(Long medicId);
}
