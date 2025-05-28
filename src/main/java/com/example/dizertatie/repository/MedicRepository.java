package com.example.dizertatie.repository;

import com.example.dizertatie.entities.Medic;
import com.example.dizertatie.entities.Pacient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicRepository extends JpaRepository<Medic, Long>  {

    Optional<Medic> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByCodParafa(Integer codParafa);


}
