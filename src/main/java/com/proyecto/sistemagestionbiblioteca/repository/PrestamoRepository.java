package com.proyecto.sistemagestionbiblioteca.repository;

import com.proyecto.sistemagestionbiblioteca.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    List<Prestamo> findByFechaDevolucionRealIsNull();

    List<Prestamo> findByFechaDevolucionEsperadaBeforeAndFechaDevolucionRealIsNull(LocalDate hoy);
    
    List<Prestamo> findBySocioIdAndFechaDevolucionRealIsNull(Long socioId);
}