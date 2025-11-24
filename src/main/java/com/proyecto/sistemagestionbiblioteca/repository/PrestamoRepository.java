package com.proyecto.sistemagestionbiblioteca.repository;

import com.proyecto.sistemagestionbiblioteca.model.Prestamo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    // Busca en el nombre del socio O en el título del libro
    @Query("SELECT p FROM Prestamo p WHERE " +
           "LOWER(p.socio.nombre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.libro.titulo) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Prestamo> buscarPorSocioOLibro(String keyword, Pageable pageable);

    // Para reportes
    List<Prestamo> findByFechaDevolucionEsperadaBeforeAndFechaDevolucionRealIsNull(LocalDate hoy);
    
    long countBySocioIdAndFechaDevolucionRealIsNull(Long socioId);

    boolean existsBySocioIdAndFechaDevolucionRealIsNullAndFechaDevolucionEsperadaBefore(Long socioId, LocalDate fecha);
}