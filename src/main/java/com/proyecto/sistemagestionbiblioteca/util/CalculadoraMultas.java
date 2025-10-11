package com.proyecto.sistemagestionbiblioteca.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CalculadoraMultas {

    // Tarifa diaria por retraso (constante de negocio)
    private static final double TARIFA_MULTA_DIARIA = 1.0; 

    /**
     * Calcula los días de retraso comparando la fecha de devolución esperada y la fecha real.
     * * @param fechaEsperada La fecha límite de devolución.
     * @param fechaReal La fecha en que el libro fue devuelto.
     * @return El número de días de retraso. Retorna 0 si no hay retraso.
     */
    public static long calcularDiasRetraso(LocalDate fechaEsperada, LocalDate fechaReal) {
        if (fechaReal.isAfter(fechaEsperada)) {
            return ChronoUnit.DAYS.between(fechaEsperada, fechaReal);
        }
        return 0;
    }
    
    /**
     * Calcula el monto total de la multa.
     * * @param diasRetraso Número de días que el libro estuvo retrasado.
     * @return El monto total a cobrar.
     */
    public static double calcularMontoMulta(long diasRetraso) {
        if (diasRetraso > 0) {
            return diasRetraso * TARIFA_MULTA_DIARIA;
        }
        return 0.0;
    }
}
