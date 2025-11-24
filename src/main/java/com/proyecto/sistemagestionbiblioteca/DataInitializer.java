package com.proyecto.sistemagestionbiblioteca;

import com.proyecto.sistemagestionbiblioteca.model.Usuario;
import com.proyecto.sistemagestionbiblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

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
            System.out.println("--> Usuario ADMIN creado: admin / Admin@123");
        }

        // Crear usuario EMPLEADO "Pepe" si no existe
        if (usuarioRepository.findByUsername("pepe").isEmpty()) {
            Usuario pepe = new Usuario();
            pepe.setUsername("pepe");
            // Contraseña 'Pepe@123' encriptada
            pepe.setPassword(passwordEncoder.encode("Pepe@123"));
            pepe.setRole(Usuario.Role.ROLE_EMPLEADO);
            pepe.setAccountLocked(false);
            pepe.setFailedAttempts(0);
            usuarioRepository.save(pepe);
            System.out.println("--> Usuario EMPLEADO creado: pepe / Pepe@123");
        }
    }
}