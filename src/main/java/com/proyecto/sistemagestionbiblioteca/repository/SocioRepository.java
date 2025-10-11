package com.proyecto.sistemagestionbiblioteca.repository;

import com.proyecto.sistemagestionbiblioteca.model.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {
    
}