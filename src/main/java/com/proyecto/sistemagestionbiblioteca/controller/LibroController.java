package com.proyecto.sistemagestionbiblioteca.controller;

import com.proyecto.sistemagestionbiblioteca.model.Libro;
import com.proyecto.sistemagestionbiblioteca.service.LibroService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/libros")
@RequiredArgsConstructor
public class LibroController {

    private final LibroService libroService;

    @GetMapping
    public String listarLibros(Model model, 
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "") String keyword) {
        
        PageRequest pageable = PageRequest.of(page, 20, Sort.by("id").ascending());
        
        Page<Libro> pageLibros = libroService.buscarTodos(keyword, pageable);

        model.addAttribute("page", pageLibros);     // Objeto Page con los datos
        model.addAttribute("keyword", keyword);     // Para mantener el texto en el buscador
        
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
        libroService.save(libro);
        attributes.addFlashAttribute("success", "Libro guardado con éxito.");
        return "redirect:/libros";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Libro libro = libroService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado."));
        
        model.addAttribute("libro", libro);
        model.addAttribute("titulo", "Editar Libro");
        return "libro/formularioLibro";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarLibro(@PathVariable Long id, RedirectAttributes attributes) {
        libroService.deleteById(id);
        attributes.addFlashAttribute("warning", "Libro eliminado.");
        return "redirect:/libros";
    }
}