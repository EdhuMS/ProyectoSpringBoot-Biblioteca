package com.proyecto.sistemagestionbiblioteca;

import com.proyecto.sistemagestionbiblioteca.model.Usuario;
import com.proyecto.sistemagestionbiblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Crear usuario ADMIN por defecto si no existe
        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            // Contraseña 'Admin@123' encriptada
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setRole(Usuario.Role.ROLE_ADMINISTRADOR);
            admin.setAccountLocked(false);
            admin.setFailedAttempts(0);
            usuarioRepository.save(admin);
        }
    }
}