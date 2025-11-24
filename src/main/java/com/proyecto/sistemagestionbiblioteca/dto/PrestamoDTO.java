package com.proyecto.sistemagestionbiblioteca.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PrestamoDTO {
    private Long id;
    private Long socioId;
    private Long libroId;
    private LocalDate fechaDevolucionEsperada;
}