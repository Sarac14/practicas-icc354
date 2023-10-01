package com.example.crudspringboot.controlador;

import com.example.crudspringboot.entidades.Estudiante;
import com.example.crudspringboot.servicios.EstudianteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
@RequestMapping(path = "/thymeleaf")
public class ThymeleafControlador {

    @Autowired
    private EstudianteServicio estudianteServicio;

    @GetMapping("/listarEstudiantes")
    public String listarEstudiantes(Model model) {
        List<Estudiante> listaDeEstudiantes = estudianteServicio.listarEstudiantes();
        model.addAttribute("lista", listaDeEstudiantes);
        return "thymeleaf/index";
    }

    @GetMapping("/borrarEstudiante/{id}")
    public String borrarEstudiante(@PathVariable Long id) {
        estudianteServicio.eliminarEstudiante(id);
        return "redirect:/thymeleaf/listarEstudiantes";
    }

    @GetMapping("/nuevoEstudiante")
    public String nuevoEstudiante(Model model) {
        model.addAttribute("titulo", "Crear Nuevo Estudiante");
        model.addAttribute("boton", "Registrar");
        model.addAttribute("estudiante", new Estudiante());
        return "/thymeleaf/NuevoEstudiante";
    }

    @GetMapping("/editarEstudiante/{id}")
    public String editarEstudiante(@PathVariable Long id, Model model) {
        Estudiante estudiante = estudianteServicio.obtenerUsuario(id);
        model.addAttribute("titulo", "Editar Estudiante");
        model.addAttribute("boton", "Actualizar");
        model.addAttribute("estudiante", estudiante);
        return "/thymeleaf/NuevoEstudiante";
    }

    @PostMapping("/guardarEstudiante")
    public String guardarEstudiante(@ModelAttribute Estudiante estudiante) {
        if (estudiante.getId() == null) {
            estudianteServicio.crearEstudiante(estudiante);
        } else {
            estudianteServicio.modificarEstudiante(estudiante.getId(), estudiante);
        }
        return "redirect:/thymeleaf/listarEstudiantes";
    }
}
