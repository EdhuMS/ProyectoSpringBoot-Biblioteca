package com.proyecto.sistemagestionbiblioteca.controller;

import com.proyecto.sistemagestionbiblioteca.model.Prestamo;
import com.proyecto.sistemagestionbiblioteca.service.LibroService;
import com.proyecto.sistemagestionbiblioteca.service.PrestamoService;
import com.proyecto.sistemagestionbiblioteca.service.SocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;
    @Autowired
    private SocioService socioService;
    @Autowired
    private LibroService libroService;

    @GetMapping
    public String listarPrestamos(Model model) {
        model.addAttribute("prestamos", prestamoService.findAll());
        return "prestamo/listaPrestamos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioPrestamo(Model model) {
        model.addAttribute("prestamo", new Prestamo());
        model.addAttribute("socios", socioService.findAll()); // Para el desplegable de Socios
        model.addAttribute("libros", libroService.findAll()); // Para el desplegable de Libros
        return "prestamo/formularioPrestamo";
    }

    @PostMapping("/guardar")
    public String guardarPrestamo(@ModelAttribute Prestamo prestamo,
            RedirectAttributes attributes) {
        try {
            // Obtenemos el ID del libro que ya viene anidado en el objeto Prestamo
            Long libroId = prestamo.getLibro().getId();

            // Llamamos al servicio con el ID del libro
            prestamoService.realizarPrestamo(prestamo, libroId);

            attributes.addFlashAttribute("success",
                    "Préstamo registrado con éxito. Fecha límite: " + prestamo.getFechaDevolucionEsperada());
        } catch (IllegalStateException | IllegalArgumentException e) {
            // Captura errores de stock o ID no encontrado del servicio
            attributes.addFlashAttribute("error", "Error al registrar el préstamo: " + e.getMessage());
        } catch (Exception e) {
            // Captura otros errores (como el de Hibernate si el binding falló)
            attributes.addFlashAttribute("error", "Ocurrió un error inesperado al guardar: " + e.getMessage());
        }
        return "redirect:/prestamos";
    }

    @GetMapping("/devolver/{id}")
    public String devolverLibro(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            Prestamo prestamoDevuelto = prestamoService.procesarDevolucion(id);

            if (prestamoDevuelto.getMontoMulta() > 0) {
                attributes.addFlashAttribute("success",
                        "Devolución procesada con multa de: $" + prestamoDevuelto.getMontoMulta());
            } else {
                attributes.addFlashAttribute("success", "Devolución procesada con éxito. ¡A tiempo!");
            }

            return "redirect:/prestamos/comprobante/" + id;

        } catch (IllegalStateException | IllegalArgumentException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/prestamos";
        }
    }

    @GetMapping("/comprobante/{id}")
    public String generarComprobante(@PathVariable Long id, Model model, RedirectAttributes attributes) {
        return prestamoService.findById(id).map(prestamo -> {

            // Validar que el libro haya sido devuelto (Es un ticket de devolución)
            if (prestamo.getFechaDevolucionReal() == null) {
                attributes.addFlashAttribute("error", "El préstamo aún no ha sido devuelto.");
                return "redirect:/prestamos";
            }

            model.addAttribute("prestamo", prestamo);

            // El cálculo de días de retraso se mantiene para el ticket.
            long diasRetraso = prestamo.getFechaDevolucionReal() != null
                    ? java.time.temporal.ChronoUnit.DAYS.between(prestamo.getFechaDevolucionEsperada(),
                            prestamo.getFechaDevolucionReal())
                    : 0;

            model.addAttribute("diasRetraso", diasRetraso);

            return "prestamo/comprobantePago"; // Retorna la vista (ticket)

        }).orElseGet(() -> {
            attributes.addFlashAttribute("error", "Préstamo no encontrado.");
            return "redirect:/prestamos";
        });
    }

    @GetMapping("/reporte/vencidos")
    public String generarReporteVencidos(Model model) {
        List<Prestamo> vencidos = prestamoService.obtenerPrestamosVencidos();
        model.addAttribute("vencidos", vencidos);
        model.addAttribute("fechaReporte", java.time.LocalDate.now());
        return "prestamo/reporteVencidos"; // Retorna la vista del reporte
    }
}