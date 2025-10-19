package com.proyecto.sistemagestionbiblioteca.config;

import com.proyecto.sistemagestionbiblioteca.model.Usuario;
import com.proyecto.sistemagestionbiblioteca.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String username = request.getParameter("username");
        String errorUrl = "/login?error=true";

        try {
            Usuario usuario = usuarioService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            if (!usuario.isAccountLocked()) {
                if (exception instanceof BadCredentialsException) {
                    usuarioService.increaseFailedAttempts(usuario);
                    int attemptsLeft = UsuarioService.MAX_FAILED_ATTEMPTS - usuario.getFailedAttempts();
                    
                    if(attemptsLeft > 0) {
                        exception = new BadCredentialsException("Clave incorrecta. Quedan " + attemptsLeft + " intentos.");
                    } else {
                        exception = new LockedException("Cuenta bloqueada. Demasiados intentos fallidos.");
                    }
                }
            } else {
                exception = new LockedException("Esta cuenta ha sido bloqueada. Contacte al administrador.");
            }

        } catch (UsernameNotFoundException e) {
             exception = new BadCredentialsException("Usuario o contraseña incorrectos.");
        }

        request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception);
        
        setDefaultFailureUrl(errorUrl);
        super.onAuthenticationFailure(request, response, exception);
    }
}