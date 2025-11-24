package com.proyecto.sistemagestionbiblioteca.repository;

import com.proyecto.sistemagestionbiblioteca.model.Socio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {
    
    // Busca por nombre O documento de identidad
    Page<Socio> findByNombreContainingIgnoreCaseOrIdentificacionContainingIgnoreCase(String nombre, String identificacion, Pageable pageable);
}