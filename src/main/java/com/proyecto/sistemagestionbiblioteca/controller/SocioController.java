package com.proyecto.sistemagestionbiblioteca.controller;

import com.proyecto.sistemagestionbiblioteca.model.Socio;
import com.proyecto.sistemagestionbiblioteca.service.SocioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/socios")
@RequiredArgsConstructor
public class SocioController {

    private final SocioService socioService;

    @GetMapping
    public String listarSocios(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "") String keyword) {

        PageRequest pageable = PageRequest.of(page, 20, Sort.by("id").ascending());

        Page<Socio> pageSocios = socioService.buscarTodos(keyword, pageable);

        model.addAttribute("page", pageSocios);
        model.addAttribute("keyword", keyword);

        return "socio/listaSocios";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("socio", new Socio());
        model.addAttribute("titulo", "Registrar Nuevo Socio");
        return "socio/formularioSocio";
    }

    @PostMapping("/guardar")
    public String guardarSocio(@ModelAttribute Socio socio, RedirectAttributes attributes) {
        socioService.save(socio);
        attributes.addFlashAttribute("success", "Socio guardado con éxito.");
        return "redirect:/socios";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Socio socio = socioService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Socio no encontrado."));

        model.addAttribute("socio", socio);
        model.addAttribute("titulo", "Editar Socio");
        return "socio/formularioSocio";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarSocio(@PathVariable Long id, RedirectAttributes attributes) {
        socioService.deleteById(id);
        attributes.addFlashAttribute("warning", "Socio eliminado.");
        return "redirect:/socios";
    }
}