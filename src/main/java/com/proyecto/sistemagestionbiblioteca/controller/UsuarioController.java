package com.proyecto.sistemagestionbiblioteca.controller;

import com.proyecto.sistemagestionbiblioteca.model.Usuario;
import com.proyecto.sistemagestionbiblioteca.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "usuario/listaUsuarios";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("titulo", "Registrar Nuevo Empleado");
        return "usuario/formularioUsuario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario, RedirectAttributes attributes) {
        try {
            // El UsuarioService ya se encarga de hashear la clave y de asignar el ROL_EMPLEADO por defecto.
            usuarioService.save(usuario);
            attributes.addFlashAttribute("success", "Empleado guardado con éxito.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al guardar el empleado: " + e.getMessage());
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model, RedirectAttributes attributes) {
        return usuarioService.findById(id).map(usuario -> {
            usuario.setPassword("********"); 
            model.addAttribute("usuario", usuario);
            model.addAttribute("titulo", "Editar Empleado");
            return "usuario/formularioUsuario";
        }).orElseGet(() -> {
            attributes.addFlashAttribute("error", "Empleado no encontrado.");
            return "redirect:/usuarios";
        });
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            usuarioService.deleteById(id);
            attributes.addFlashAttribute("warning", "Empleado eliminado.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "No se puede eliminar el empleado.");
        }
        return "redirect:/usuarios";
    }
    
    @GetMapping("/desbloquear/{id}")
    public String desbloquearUsuario(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            usuarioService.unlockAccount(id);
            attributes.addFlashAttribute("success", "Usuario desbloqueado con éxito.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al desbloquear.");
        }
        return "redirect:/usuarios";
    }
}