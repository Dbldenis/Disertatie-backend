package com.example.dizertatie.repository;

import com.example.dizertatie.entities.Pacient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacientRepository extends JpaRepository<Pacient, Long> {

    Optional<Pacient> findByEmail(String email);
}

