package com.proyecto.sistemagestionbiblioteca.controller;

import com.proyecto.sistemagestionbiblioteca.dto.UsuarioDTO;
import com.proyecto.sistemagestionbiblioteca.model.Usuario;
import com.proyecto.sistemagestionbiblioteca.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "usuario/listaUsuarios";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        model.addAttribute("titulo", "Registrar Nuevo Empleado");
        return "usuario/formularioUsuario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute UsuarioDTO usuarioDTO, RedirectAttributes attributes) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.getId());
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(usuarioDTO.getPassword());
        usuarioService.save(usuario);
        
        attributes.addFlashAttribute("success", "Empleado guardado con éxito.");
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado."));
        
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setPassword("********");
        
        model.addAttribute("usuarioDTO", dto);
        model.addAttribute("titulo", "Editar Empleado");
        return "usuario/formularioUsuario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes attributes) {
        usuarioService.deleteById(id);
        attributes.addFlashAttribute("warning", "Empleado eliminado.");
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