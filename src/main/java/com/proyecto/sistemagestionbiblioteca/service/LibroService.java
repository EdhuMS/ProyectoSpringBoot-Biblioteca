package com.proyecto.sistemagestionbiblioteca.service;

import com.proyecto.sistemagestionbiblioteca.model.Libro;
import com.proyecto.sistemagestionbiblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> findAll() {
        return libroRepository.findAll();
    }

    public Optional<Libro> findById(Long id) {
        return libroRepository.findById(id);
    }

    public Libro save(Libro libro) {
        return libroRepository.save(libro);
    }

    public void deleteById(Long id) {
        libroRepository.deleteById(id);
    }

    /**
     * Lógica clave: Disminuye la cantidad disponible después de un préstamo.
     * @param libroId ID del libro prestado.
     * @param cantidad Cantidad a disminuir.
     * @throws IllegalStateException si no hay stock disponible.
     */
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

    /**
     * Lógica clave: Aumenta la cantidad disponible después de una devolución.
     * @param libroId ID del libro devuelto.
     * @param cantidad Cantidad a aumentar.
     */
    public void aumentarStock(Long libroId, int cantidad) {
        Libro libro = libroRepository.findById(libroId)
                                     .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado."));

        libro.setCantidadDisponible(libro.getCantidadDisponible() + cantidad);
        libroRepository.save(libro);
    }
}