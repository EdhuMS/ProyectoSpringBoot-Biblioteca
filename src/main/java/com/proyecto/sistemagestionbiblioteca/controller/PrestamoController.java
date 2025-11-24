package com.proyecto.sistemagestionbiblioteca.controller;

import com.proyecto.sistemagestionbiblioteca.dto.PrestamoDTO;
import com.proyecto.sistemagestionbiblioteca.model.Libro;
import com.proyecto.sistemagestionbiblioteca.model.Prestamo;
import com.proyecto.sistemagestionbiblioteca.model.Socio;
import com.proyecto.sistemagestionbiblioteca.service.LibroService;
import com.proyecto.sistemagestionbiblioteca.service.PrestamoService;
import com.proyecto.sistemagestionbiblioteca.service.SocioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/prestamos")
@RequiredArgsConstructor
public class PrestamoController {

    private final PrestamoService prestamoService;
    private final SocioService socioService;
    private final LibroService libroService;

    @GetMapping
    public String listarPrestamos(Model model,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "") String keyword) {
        
        PageRequest pageable = PageRequest.of(page, 20, Sort.by("id").ascending());

        Page<Prestamo> pagePrestamos = prestamoService.buscarTodos(keyword, pageable);

        model.addAttribute("page", pagePrestamos);
        model.addAttribute("keyword", keyword);
        
        return "prestamo/listaPrestamos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioPrestamo(Model model) {
        model.addAttribute("prestamoDTO", new PrestamoDTO());
        model.addAttribute("socios", socioService.findAll());
        model.addAttribute("libros", libroService.findAll());
        return "prestamo/formularioPrestamo";
    }

    @PostMapping("/guardar")
    public String guardarPrestamo(@ModelAttribute PrestamoDTO prestamoDTO, RedirectAttributes attributes) {
        // 1. Convertir IDs de DTO a Entidades (Validando existencia)
        Socio socio = socioService.findById(prestamoDTO.getSocioId())
                .orElseThrow(() -> new IllegalArgumentException("Socio no válido."));

        Libro libro = libroService.findById(prestamoDTO.getLibroId())
                .orElseThrow(() -> new IllegalArgumentException("Libro no válido."));

        // 2. Armar objeto Prestamo
        Prestamo prestamo = new Prestamo();
        prestamo.setSocio(socio);
        prestamo.setLibro(libro);
        prestamo.setFechaDevolucionEsperada(prestamoDTO.getFechaDevolucionEsperada());

        // 3. Ejecutar préstamo (baja stock)
        prestamoService.realizarPrestamo(prestamo, libro.getId());

        attributes.addFlashAttribute("success", 
            "Préstamo registrado con éxito. Vence: " + prestamo.getFechaDevolucionEsperada());
        return "redirect:/prestamos";
    }

    @GetMapping("/devolver/{id}")
    public String devolverLibro(@PathVariable Long id, RedirectAttributes attributes) {
        Prestamo prestamoDevuelto = prestamoService.procesarDevolucion(id);

        if (prestamoDevuelto.getMontoMulta() > 0) {
            attributes.addFlashAttribute("success", "Devolución con multa: S/" + prestamoDevuelto.getMontoMulta());
        } else {
            attributes.addFlashAttribute("success", "Devolución exitosa. ¡A tiempo!");
        }

        return "redirect:/prestamos/comprobante/" + id;
    }

    @GetMapping("/comprobante/{id}")
    public String generarComprobante(@PathVariable Long id, Model model) {
        Prestamo prestamo = prestamoService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado."));

        if (prestamo.getFechaDevolucionReal() == null) {
            throw new IllegalStateException("El préstamo aún no ha sido devuelto.");
        }

        model.addAttribute("prestamo", prestamo);

        long diasRetraso = java.time.temporal.ChronoUnit.DAYS.between(
                prestamo.getFechaDevolucionEsperada(), prestamo.getFechaDevolucionReal());
        
        model.addAttribute("diasRetraso", Math.max(diasRetraso, 0));

        return "prestamo/comprobantePago";
    }

    @GetMapping("/reporte/vencidos")
    public String generarReporteVencidos(Model model) {
        List<Prestamo> vencidos = prestamoService.obtenerPrestamosVencidos();
        model.addAttribute("vencidos", vencidos);
        model.addAttribute("fechaReporte", java.time.LocalDate.now());
        return "prestamo/reporteVencidos";
    }
}