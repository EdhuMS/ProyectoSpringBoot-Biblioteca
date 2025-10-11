package com.proyecto.sistemagestionbiblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación Muchos a Uno: Varios préstamos pertenecen a un Socio
    @ManyToOne
    @JoinColumn(name = "socio_id", nullable = false)
    private Socio socio;

    // Relación Muchos a Uno: Varios préstamos se refieren a un Libro
    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;
    
    // Fechas de Control
    @Column(nullable = false)
    private LocalDate fechaPrestamo = LocalDate.now(); // Se establece automáticamente al crear

    @Column(nullable = false)
    private LocalDate fechaDevolucionEsperada; // Calculada por el Servicio al registrar

    private LocalDate fechaDevolucionReal; // Se establece al devolver el libro

    // Campos para el Comprobante de Pago (Multa)
    private Double montoMulta; // Monto calculado por retraso
    
    private Boolean pagado = false; // Indica si la multa ya fue pagada

}