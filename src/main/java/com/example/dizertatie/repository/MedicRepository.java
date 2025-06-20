package com.example.dizertatie.repository;

import com.example.dizertatie.entities.Medic;
import com.example.dizertatie.entities.Pacient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicRepository extends JpaRepository<Medic, Long>  {

    Optional<Medic> findByEmail(String email);

    boolean existsByUtilizatorAndParola(String utilizator, String parola);

    List<Medic> findBySpecializareIgnoreCase(String specializare);

    Optional<Medic> findByUtilizator(String utilizator);

}
