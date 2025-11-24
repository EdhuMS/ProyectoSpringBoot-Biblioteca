package com.proyecto.sistemagestionbiblioteca.service;

import com.proyecto.sistemagestionbiblioteca.model.Socio;
import com.proyecto.sistemagestionbiblioteca.repository.SocioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SocioService {

    private final SocioRepository socioRepository;

    // --- BÚSQUEDA Y PAGINACIÓN ---
    public Page<Socio> buscarTodos(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            return socioRepository.findByNombreContainingIgnoreCaseOrIdentificacionContainingIgnoreCase(keyword, keyword, pageable);
        }
        return socioRepository.findAll(pageable);
    }
    
    // Para selects
    public List<Socio> findAll() {
        return socioRepository.findAll();
    }

    // --- CRUD ---
    public Optional<Socio> findById(Long id) {
        return socioRepository.findById(id);
    }

    public Socio save(Socio socio) {
        return socioRepository.save(socio);
    }

    public void deleteById(Long id) {
        socioRepository.deleteById(id);
    }
}