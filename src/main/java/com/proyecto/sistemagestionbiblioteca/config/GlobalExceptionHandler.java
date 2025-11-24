package com.proyecto.sistemagestionbiblioteca.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Maneja errores de negocio esperados (Stock insuficiente, Usuario no encontrado, Reglas de negocio).
    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class, SecurityException.class})
    public String handleBusinessException(Exception ex, RedirectAttributes attributes, HttpServletRequest request) {
        // Inyectamos el mensaje de error para que Thymeleaf lo muestre
        attributes.addFlashAttribute("error", ex.getMessage());
        
        // Redirigimos al usuario a la página de donde vino (Referer)
        return "redirect:" + getPreviousPage(request);
    }

    //Maneja errores de base de datos (ej: Duplicados de clave única como DNI o Username).
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDatabaseErrors(DataIntegrityViolationException ex, RedirectAttributes attributes, HttpServletRequest request) {
        attributes.addFlashAttribute("error", "Error de integridad de datos: Posible registro duplicado (DNI, ISBN o Usuario ya existen).");
        return "redirect:" + getPreviousPage(request);
    }

    //Maneja cualquier otro error no controlado (NullPointer, fallos del servidor).
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, RedirectAttributes attributes, HttpServletRequest request) {
        ex.printStackTrace(); // Imprimir en consola para debug del desarrollador
        attributes.addFlashAttribute("error", "Ocurrió un error inesperado en el sistema. Contacte al soporte.");
        return "redirect:" + getPreviousPage(request);
    }

    // Método auxiliar para obtener la URL anterior
    private String getPreviousPage(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        return (referer != null && !referer.isEmpty()) ? referer : "/";
    }
}