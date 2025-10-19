package com.proyecto.sistemagestionbiblioteca.service;

import com.proyecto.sistemagestionbiblioteca.model.Usuario;
import com.proyecto.sistemagestionbiblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    public static final int MAX_FAILED_ATTEMPTS = 5;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    // MÉTODOS DE UserDetailsService(PARA SPRING SECURITY)

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                !usuario.isAccountLocked(), // enabled (si no está bloqueado)
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                Collections.singletonList(new SimpleGrantedAuthority(usuario.getRole().name()))
        );
    }

    // MÉTODOS PARA EL BLOQUEO DE CUENTAS

    public void increaseFailedAttempts(Usuario user) {
        int newFailAttempts = user.getFailedAttempts() + 1;
        user.setFailedAttempts(newFailAttempts);

        if (newFailAttempts >= MAX_FAILED_ATTEMPTS) {
            user.setAccountLocked(true);
        }
        usuarioRepository.save(user);
    }

    public void resetFailedAttempts(Usuario user) {
        user.setFailedAttempts(0);
        usuarioRepository.save(user);
    }

    public void unlockAccount(Long id) {
        Usuario user = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setAccountLocked(false);
        user.setFailedAttempts(0);
        usuarioRepository.save(user);
    }

    // MÉTODOS CRUD (PARA MANTENIMIENTO)
    
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Usuario save(Usuario usuario) {
        // Encriptar contraseña solo si es nueva o se ha modificado
        if (usuario.getId() == null || !usuario.getPassword().startsWith("$2a$")) {
             usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        usuario.setRole(Usuario.Role.ROLE_EMPLEADO); // Solo permite crear empleados
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }
}