package com.proyecto.sistemagestionbiblioteca.service;

import com.proyecto.sistemagestionbiblioteca.model.Prestamo;
import com.proyecto.sistemagestionbiblioteca.repository.PrestamoRepository;
import com.proyecto.sistemagestionbiblioteca.util.CalculadoraMultas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoService {
    
    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LibroService libroService;
    
    public List<Prestamo> findAll() {
        return prestamoRepository.findAll();
    }
    
    public Optional<Prestamo> findById(Long id) {
        return prestamoRepository.findById(id);
    }

    public Prestamo realizarPrestamo(Prestamo nuevoPrestamo, Long libroId) {
        // Validar y Disminuir el Stock del Libro
        libroService.disminuirStock(libroId, 1); 

        // Establecer la fecha de devolución esperada
        if (nuevoPrestamo.getFechaDevolucionEsperada() == null) {
            nuevoPrestamo.setFechaDevolucionEsperada(LocalDate.now().plusDays(7));
        }
        
        // Guardar el préstamo
        return prestamoRepository.save(nuevoPrestamo);
    }

    // Lógica de Devolución y Multa (Comprobante de Pago)

    /**
     * Procesa la devolución de un libro y calcula la multa si aplica.
     * @param prestamoId ID del préstamo a devolver.
     * @return El objeto Prestamo actualizado con la multa.
     */
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

        long diasRetraso = CalculadoraMultas.calcularDiasRetraso(
            prestamo.getFechaDevolucionEsperada(), 
            prestamo.getFechaDevolucionReal()
        );
        
        if (diasRetraso > 0) {
            double monto = CalculadoraMultas.calcularMontoMulta(diasRetraso);
            prestamo.setMontoMulta(monto);
            prestamo.setPagado(true); 
        } else {
            prestamo.setMontoMulta(0.0);
            prestamo.setPagado(true);
        }

        // Guardar el estado final del préstamo
        return prestamoRepository.save(prestamo);
    }
    
    // Lógica de Reportes
    public List<Prestamo> obtenerPrestamosVencidos() {
        return prestamoRepository.findByFechaDevolucionEsperadaBeforeAndFechaDevolucionRealIsNull(LocalDate.now());
    }
}