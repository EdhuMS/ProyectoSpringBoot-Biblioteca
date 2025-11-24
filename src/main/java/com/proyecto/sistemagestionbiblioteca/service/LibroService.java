package com.proyecto.sistemagestionbiblioteca.service;

import com.proyecto.sistemagestionbiblioteca.model.Libro;
import com.proyecto.sistemagestionbiblioteca.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibroService {

    private final LibroRepository libroRepository;

    // --- BÚSQUEDA Y PAGINACIÓN ---
    public Page<Libro> buscarTodos(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            return libroRepository.findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(keyword, keyword, pageable);
        }
        return libroRepository.findAll(pageable);
    }
    
    // Método para llenar combos (selects) sin paginar
    public List<Libro> findAll() {
        return libroRepository.findAll();
    }

    // --- CRUD ---
    public Optional<Libro> findById(Long id) {
        return libroRepository.findById(id);
    }

    public Libro save(Libro libro) {
        return libroRepository.save(libro);
    }

    public void deleteById(Long id) {
        libroRepository.deleteById(id);
    }

    // --- LÓGICA DE STOCK ---
    public void disminuirStock(Long libroId, int cantidad) {
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado."));

        if (libro.getCantidadDisponible() >= cantidad) {
            libro.setCantidadDisponible(libro.getCantidadDisponible() - cantidad);
            libroRepository.save(libro);
        } else {
            throw new IllegalStateException("Stock insuficiente para el préstamo.");
        }
    }

    public void aumentarStock(Long libroId, int cantidad) {
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado."));

        libro.setCantidadDisponible(libro.getCantidadDisponible() + cantidad);
        libroRepository.save(libro);
    }
}