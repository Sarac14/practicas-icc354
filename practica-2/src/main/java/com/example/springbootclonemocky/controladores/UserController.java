package com.example.springbootclonemocky.controladores;

import com.example.springbootclonemocky.config.SecurityConfig;
import com.example.springbootclonemocky.entidades.Rol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityConfig securityConfig;

    @Value("${server.port}")
    private int puerto;

    @GetMapping("/")
    public String redireccion() {
        return "redirect:/inicio";
    }

    @GetMapping("/inicio")
    public String inicio (Model model) {
       // model.addAttribute("titulo", "Crear Nuevo Usuario");
       model.addAttribute("usuarioLog", securityConfig.getLoggedInUserDetails());
        model.addAttribute("puerto", System.getProperty("server.port"));


        return "index";
    }

    @GetMapping("/acceso-negado")
    public ResponseEntity<String> accesoDenegado() {
        String htmlContent = "<html><body><script>alert('No tienes permiso para acceder a esta página.'); window.location='/';</script></body></html>";
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(htmlContent);

    }

    @GetMapping("/user/register")
    public String nuevoUsuario(Model model) {
        model.addAttribute("titulo", "Crear Nuevo Usuario");
        model.addAttribute("usuario", new Usuario());
        return "RegistrarUsuario";
    }

    @PostMapping("/user/register")
    public String registerUser(@ModelAttribute Usuario user) {
        Rol rolUsuario = new Rol("ROLE_USER");
        user.setRoles(new HashSet<>(Arrays.asList(rolUsuario)));
        userService.saveUsuario(user);
        return "redirect:/user/listarUsuarios";
    }

    @GetMapping("/user/login")
    public String iniciarSesion(Model model) {
        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/user/listarUsuarios";
        }*/
        model.addAttribute("titulo", "login");
        model.addAttribute("puerto", puerto);
        return "login";
    }

    @GetMapping("/user/logout")
    public String cerrarSesion(Model model) {
        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/user/listarUsuarios";
        }*/
       // model.addAttribute("titulo", "login");
        return "redirect:/inicio";
    }



    @GetMapping("/user/listarUsuarios")
    public String listarEstudiantes(Model model) {
        List<Usuario> listaUsuarios = userService.listarUsuarios();
        model.addAttribute("lista", listaUsuarios);
        if(securityConfig.getLoggedInUserDetails().getUsername() != null) {
            System.out.println("------------------------" + securityConfig.getLoggedInUserDetails().getUsername() + "--------------------------");

        }else{
            System.out.println("------------------------" + "Usuario Nulo" + "--------------------------");

        }
        return "listarUsuarios";
    }

}
