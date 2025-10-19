package com.proyecto.sistemagestionbiblioteca.config;

import com.proyecto.sistemagestionbiblioteca.model.Usuario;
import com.proyecto.sistemagestionbiblioteca.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
        String username = authentication.getName();
        Usuario usuario = usuarioService.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Error al resetear intentos"));

        // Si el usuario tenía intentos fallidos, los resetea
        if (usuario.getFailedAttempts() > 0) {
            usuarioService.resetFailedAttempts(usuario);
        }

        setDefaultTargetUrl("/"); // Redirige al inicio
        super.onAuthenticationSuccess(request, response, authentication);
    }
}