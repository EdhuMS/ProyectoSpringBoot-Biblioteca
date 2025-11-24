package com.proyecto.sistemagestionbiblioteca.repository;

import com.proyecto.sistemagestionbiblioteca.model.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Busca por Título O Autor (ignorando mayúsculas/minúsculas)
    Page<Libro> findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(String titulo, String autor, Pageable pageable);
    
    // Método auxiliar para validaciones
    Libro findByIsbn(String isbn);
}