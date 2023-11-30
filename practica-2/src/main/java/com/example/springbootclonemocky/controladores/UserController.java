package com.example.springbootclonemocky.controladores;

import com.example.springbootclonemocky.repositorio.RolRepository;
import com.example.springbootclonemocky.servicios.SeguridadServices;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import com.example.springbootclonemocky.entidades.Usuario;
import com.example.springbootclonemocky.servicios.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    SeguridadServices seguridadServices;
    @Autowired
    RolRepository rolRepository;

    @GetMapping("/acceso-negado")
    public ResponseEntity<String> accesoDenegado() {
        String htmlContent = "<html><body><script>alert('No tienes permiso para acceder a esta página.'); window.location='/';</script></body></html>";
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(htmlContent);

    }

    @GetMapping("/login")
    public String iniciarSesion(Model model) {
        model.addAttribute("titulo", "login");
        return "login";
    }

    @GetMapping("/logout")
    public String cerrarSesion(Model model) {

        return "redirect:/inicio";
    }


    @GetMapping("/listarUsuarios")
    public String listado(Model model){

        Usuario user = seguridadServices.getAuthorizedUser();
        if(user == null){
            return "redirect:/login";
        }

        if (!user.getRols().get(0).getRole().equalsIgnoreCase("ROLE_ADMIN")){
            System.out.println("Not allowed");
            return "redirect:/";
        }

        //model.addAttribute("titulo", "CRUD Usuario");
        model.addAttribute("lista", userService.findAll());
        return "listarUsuarios";
    }

    @GetMapping("/register")
    public String crearUsuario(Model model){

        model.addAttribute("titulo", "Crear Nuevo Usuario");
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("accion","/user/register");

        return "RegistrarUsuario";
    }

    @PostMapping("/register")
    public String crearPost(@RequestParam("username") String username, @RequestParam("password") String password,
                            @RequestParam(name = "admin", required = false) String admin)
    {

        Usuario usuario = seguridadServices.getAuthorizedUser();
//        if (usuario == null){
//            return "redirect:/";
//        }

        if (!usuario.getRols().get(0).getRole().equalsIgnoreCase("ROLE_ADMIN")){
            return "redirect:/";
        }

        userService.crearUsuario(username, password, admin != null);

        return "redirect:/user/listarUsuarios";
    }


}
