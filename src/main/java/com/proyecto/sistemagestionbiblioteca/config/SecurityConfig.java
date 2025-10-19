package com.proyecto.sistemagestionbiblioteca.config;

import com.proyecto.sistemagestionbiblioteca.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler;
    
    @Autowired
    private AuthenticationFailureHandler customAuthenticationFailureHandler;

    // Bean para encriptar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // El "proveedor" que usa Spring Security para buscar usuarios
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioService); // Usa nuestro servicio
        provider.setPasswordEncoder(passwordEncoder()); // Usa BCrypt
        return provider;
    }

    // Cadena de filtros de seguridad (Reglas de acceso)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                
                // Mantenimiento de Usuarios -> SOLO ADMIN
                .requestMatchers("/usuarios/**").hasRole("ADMINISTRADOR")
                
                // Menús de la aplicación -> ADMIN y EMPLEADO
                .requestMatchers(
                    "/", "/index",
                    "/libros/**", 
                    "/socios/**", 
                    "/prestamos/**"
                ).hasAnyRole("ADMINISTRADOR", "EMPLEADO")
                
                // Cualquier otra petición, requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/perform_login") 
                .successHandler(customAuthenticationSuccessHandler) // Qué hacer si el login es exitoso
                .failureHandler(customAuthenticationFailureHandler) // Qué hacer si falla
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/perform_logout")
                .logoutSuccessUrl("/login?logout=true")
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .authenticationProvider(authenticationProvider()); // Usar nuestro proveedor

        return http.build();
    }
}