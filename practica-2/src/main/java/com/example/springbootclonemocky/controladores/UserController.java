package com.example.springbootclonemocky.controladores;

import com.example.springbootclonemocky.entidades.Rol;
import org.springframework.ui.Model;
import com.example.springbootclonemocky.entidades.Usuario;
import com.example.springbootclonemocky.servicios.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String nuevoUsuario(Model model) {
        model.addAttribute("titulo", "Crear Nuevo Usuario");
        model.addAttribute("usuario", new Usuario());
        return "RegistrarUsuario";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Usuario user) {
        Rol rolUsuario = new Rol("ROLE_USER");
        user.setRoles(new HashSet<>(Arrays.asList(rolUsuario)));
        userService.saveUsuario(user);
        return "redirect:/user/listarUsuarios";
    }

    @GetMapping("/login")
    public String iniciarSesion(Model model) {
        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/user/listarUsuarios";
        }*/
        model.addAttribute("titulo", "login");
        return "login";
    }

    @GetMapping("/listarUsuarios")
    public String listarEstudiantes(Model model) {
        List<Usuario> listaUsuarios = userService.listarUsuarios();
        model.addAttribute("lista", listaUsuarios);
        return "formulario";
    }

}
