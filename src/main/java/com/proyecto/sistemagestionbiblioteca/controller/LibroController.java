package com.proyecto.sistemagestionbiblioteca.controller;

import com.proyecto.sistemagestionbiblioteca.model.Libro;
import com.proyecto.sistemagestionbiblioteca.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    public String listarLibros(Model model) {
        model.addAttribute("libros", libroService.findAll());
        return "libro/listaLibros";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("libro", new Libro());
        model.addAttribute("titulo", "Registrar Nuevo Libro");
        return "libro/formularioLibro";
    }

    @PostMapping("/guardar")
    public String guardarLibro(@ModelAttribute Libro libro, RedirectAttributes attributes) {
        try {
            libroService.save(libro);
            attributes.addFlashAttribute("success", "Libro guardado con éxito.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al guardar el libro: " + e.getMessage());
        }
        return "redirect:/libros";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model, RedirectAttributes attributes) {
        return libroService.findById(id).map(libro -> {
            model.addAttribute("libro", libro);
            model.addAttribute("titulo", "Editar Libro");
            return "libro/formularioLibro";
        }).orElseGet(() -> {
            attributes.addFlashAttribute("error", "Libro no encontrado.");
            return "redirect:/libros";
        });
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarLibro(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            libroService.deleteById(id);
            attributes.addFlashAttribute("warning", "Libro eliminado.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "No se puede eliminar el libro. Verifique que no esté prestado.");
        }
        return "redirect:/libros";
    }
}