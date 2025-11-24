package com.proyecto.sistemagestionbiblioteca.controller;

import com.proyecto.sistemagestionbiblioteca.repository.LibroRepository;
import com.proyecto.sistemagestionbiblioteca.repository.PrestamoRepository;
import com.proyecto.sistemagestionbiblioteca.repository.SocioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/busqueda")
@RequiredArgsConstructor
public class BusquedaRestController {

    private final LibroRepository libroRepository;
    private final SocioRepository socioRepository;
    private final PrestamoRepository prestamoRepository;

    @GetMapping("/libros")
    public List<Map<String, ?>> buscarLibros(@RequestParam String query) {
        return libroRepository.findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(
                query, query, PageRequest.of(0, 15, Sort.by("titulo").ascending())
        ).stream().map(libro -> Map.of(
            "id", libro.getId(),
            "texto", libro.getTitulo() + " - " + libro.getAutor() + " (ISBN: " + libro.getIsbn() + ")",
            "stock", libro.getCantidadDisponible()
        )).collect(Collectors.toList());
    }

    @GetMapping("/socios")
    public List<Map<String, ?>> buscarSocios(@RequestParam String query) {
        return socioRepository.findByNombreContainingIgnoreCaseOrIdentificacionContainingIgnoreCase(
                query, query, PageRequest.of(0, 10, Sort.by("nombre").ascending())
        ).stream().map(socio -> {
            // Consultamos el estado del socio en tiempo real
            long activos = prestamoRepository.countBySocioIdAndFechaDevolucionRealIsNull(socio.getId());
            boolean moroso = prestamoRepository.existsBySocioIdAndFechaDevolucionRealIsNullAndFechaDevolucionEsperadaBefore(socio.getId(), java.time.LocalDate.now());

            return Map.of(
                "id", socio.getId(),
                "texto", socio.getNombre() + " - DNI: " + socio.getIdentificacion(),
                "activos", activos,   // Enviamos cantidad
                "moroso", moroso      // Enviamos si es moroso
            );
        }).collect(Collectors.toList());
    }
}