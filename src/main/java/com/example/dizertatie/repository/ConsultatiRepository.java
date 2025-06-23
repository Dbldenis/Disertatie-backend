
package com.example.dizertatie.repository;

import com.example.dizertatie.entities.Consultatie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsultatiRepository extends JpaRepository<Consultatie, Long> {

    @Query("SELECT c FROM Consultatie c WHERE c.fisaPacientului.medic.id = :medicId AND c.fisaPacientului.pacient.id = :pacientId")
    List<Consultatie> findByMedicIdAndPacientId(@Param("medicId") Long medicId, @Param("pacientId") Long pacientId);

}

