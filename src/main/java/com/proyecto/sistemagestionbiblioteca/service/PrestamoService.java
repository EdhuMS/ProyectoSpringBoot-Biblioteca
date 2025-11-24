package com.proyecto.sistemagestionbiblioteca.service;

import com.proyecto.sistemagestionbiblioteca.model.Prestamo;
import com.proyecto.sistemagestionbiblioteca.repository.PrestamoRepository;
import com.proyecto.sistemagestionbiblioteca.util.CalculadoraMultas;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrestamoService {
    
    private final PrestamoRepository prestamoRepository;
    private final LibroService libroService;

    // --- BÚSQUEDA Y PAGINACIÓN ---
    public Page<Prestamo> buscarTodos(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            return prestamoRepository.buscarPorSocioOLibro(keyword, pageable);
        }
        return prestamoRepository.findAll(pageable);
    }
    
    public List<Prestamo> findAll() {
        return prestamoRepository.findAll();
    }
    
    public Optional<Prestamo> findById(Long id) {
        return prestamoRepository.findById(id);
    }

    // --- TRANSACCIONES PRINCIPALES ---

    @Transactional // Si falla algo al guardar, se revierte la bajada de stock
    public Prestamo realizarPrestamo(Prestamo nuevoPrestamo, Long libroId) {
        // Validar y Disminuir el Stock
        libroService.disminuirStock(libroId, 1); 

        // Calcular fecha
        if (nuevoPrestamo.getFechaDevolucionEsperada() == null) {
            nuevoPrestamo.setFechaDevolucionEsperada(LocalDate.now().plusDays(7));
        }
        
        // Guardar
        return prestamoRepository.save(nuevoPrestamo);
    }

    @Transactional
    public Prestamo procesarDevolucion(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
            .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado."));

        if (prestamo.getFechaDevolucionReal() != null) {
            throw new IllegalStateException("Este libro ya fue devuelto.");
        }
        
        // Aumentar Stock
        libroService.aumentarStock(prestamo.getLibro().getId(), 1);

        // Establecer fecha real
        prestamo.setFechaDevolucionReal(LocalDate.now());

        // Calcular Multa
        long diasRetraso = CalculadoraMultas.calcularDiasRetraso(
            prestamo.getFechaDevolucionEsperada(), 
            prestamo.getFechaDevolucionReal()
        );
        
        if (diasRetraso > 0) {
            double monto = CalculadoraMultas.calcularMontoMulta(diasRetraso);
            prestamo.setMontoMulta(monto);
        } else {
            prestamo.setMontoMulta(0.0);
        }
        
        prestamo.setPagado(true);

        return prestamoRepository.save(prestamo);
    }
    
    // --- REPORTES ---
    public List<Prestamo> obtenerPrestamosVencidos() {
        return prestamoRepository.findByFechaDevolucionEsperadaBeforeAndFechaDevolucionRealIsNull(LocalDate.now());
    }
}