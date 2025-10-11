package com.proyecto.sistemagestionbiblioteca.controller;

import com.proyecto.sistemagestionbiblioteca.model.Socio;
import com.proyecto.sistemagestionbiblioteca.service.SocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/socios")
public class SocioController {

    @Autowired
    private SocioService socioService;

    @GetMapping
    public String listarSocios(Model model) {
        model.addAttribute("socios", socioService.findAll());
        return "socio/listaSocios";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("socio", new Socio()); // Pasa un objeto Socio vacío al formulario
        model.addAttribute("titulo", "Registrar Nuevo Socio");
        return "socio/formularioSocio";
    }

    @PostMapping("/guardar")
    public String guardarSocio(@ModelAttribute Socio socio, RedirectAttributes attributes) {
        try {
            socioService.save(socio);
            attributes.addFlashAttribute("success", "Socio guardado con éxito.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al guardar el socio: " + e.getMessage());
        }
        return "redirect:/socios"; // Redirige a la lista de socios
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model, RedirectAttributes attributes) {
        return socioService.findById(id).map(socio -> {
            model.addAttribute("socio", socio);
            model.addAttribute("titulo", "Editar Socio");
            return "socio/formularioSocio";
        }).orElseGet(() -> {
            attributes.addFlashAttribute("error", "Socio no encontrado.");
            return "redirect:/socios";
        });
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarSocio(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            socioService.deleteById(id);
            attributes.addFlashAttribute("warning", "Socio eliminado.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "No se puede eliminar el socio. Verifique que no tenga préstamos activos.");
        }
        return "redirect:/socios";
    }
}