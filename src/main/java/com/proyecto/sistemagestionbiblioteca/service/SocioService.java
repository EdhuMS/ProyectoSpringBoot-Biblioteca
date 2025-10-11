package com.proyecto.sistemagestionbiblioteca.service;

import com.proyecto.sistemagestionbiblioteca.model.Socio;
import com.proyecto.sistemagestionbiblioteca.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocioService {

    @Autowired
    private SocioRepository socioRepository;

    // Obtener todos los socios
    public List<Socio> findAll() {
        return socioRepository.findAll();
    }

    // Obtener un socio por ID
    public Optional<Socio> findById(Long id) {
        return socioRepository.findById(id);
    }

    // Guardar o actualizar un socio
    public Socio save(Socio socio) {
        return socioRepository.save(socio);
    }

    // Eliminar un socio
    public void deleteById(Long id) {
        socioRepository.deleteById(id);
    }
}