package com.proyecto.sistemagestionbiblioteca.repository;

import com.proyecto.sistemagestionbiblioteca.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    
    Libro findByIsbn(String isbn);
}