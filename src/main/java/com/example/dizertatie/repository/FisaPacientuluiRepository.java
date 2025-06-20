package com.example.dizertatie.repository;

import com.example.dizertatie.entities.FisaPacientului;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FisaPacientuluiRepository extends JpaRepository<FisaPacientului, Long> {

    /*@Modifying
    @Query("DELETE FROM FisaPacientului f WHERE f.pacient.id = :pacientId")*/
    void deleteByPacientId(@Param("pacientId") Long pacientId);

    Optional<FisaPacientului> findByPacientIdAndMedicId(Long id, Long medicId);

    Optional<FisaPacientului> findByPacientId(Long id);
}
