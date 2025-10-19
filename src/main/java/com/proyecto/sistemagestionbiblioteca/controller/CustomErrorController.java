package com.proyecto.sistemagestionbiblioteca.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
        
            // Si el error es 404 (No Encontrado)
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            }
        }
        
        // Para cualquier otro error (500, 403, etc.) mostramos una página de error genérica.
        return "error/genericError";
    }
}