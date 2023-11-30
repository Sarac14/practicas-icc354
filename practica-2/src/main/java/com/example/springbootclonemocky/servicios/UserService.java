package com.example.springbootclonemocky.servicios;

import com.example.springbootclonemocky.entidades.Rol;
import com.example.springbootclonemocky.entidades.Usuario;
import com.example.springbootclonemocky.repositorio.RolRepository;
import com.example.springbootclonemocky.repositorio.seguridad.UsuarioRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    UsuarioRepository usuarioRepository;
    RolRepository rolRepository;
    private PasswordEncoder passwordEncoder;

    UserService(RolRepository rolRepository, UsuarioRepository usuarioRepository){
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        passwordEncoder = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        return passwordEncoder;
    }

    public Usuario crearUsuario(@NonNull String username, @NonNull String password, boolean admin, Boolean activo){

        Rol rolAdmin = rolRepository.findByRole("ROLE_ADMIN");
        Rol rolSUser = rolRepository.findByRole("ROLE_USER");

        Usuario user = new Usuario();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
//        user.setActivo(activo);
        if (admin){
            user.setRols(Arrays.asList(rolAdmin));
        }else{
            user.setRols(Arrays.asList(rolSUser));
        }
        usuarioRepository.save(user);
        return user;
    }

    public Usuario crearUsuario(@NonNull String username, @NonNull String password,  boolean admin){
        return crearUsuario(username, password, admin,true);
    }

    public Usuario findByUsername(@NonNull String username){
        return usuarioRepository.findByUsername(username);
    }

    public Usuario findUsuarioByUsernameAndPassword(String username, String password){
        return usuarioRepository.findUsuarioByUsernameAndPassword(username, password);
    }

    public void eliminar(String username){
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario != null){
            usuarioRepository.delete(usuario);
        }
    }
    public void editar(Usuario usuario){
        usuarioRepository.save(usuario);
    }


    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public void initializeUsuario(){

        Rol rolAdmin = rolRepository.findByRole("ROLE_ADMIN");
        if (rolAdmin != null){
            return;
        }
        System.out.println("Creación del usuario y rol en la base de datos");

        rolAdmin = new Rol("ROLE_ADMIN");
        rolRepository.save(rolAdmin);

        Rol rolUsuario = new Rol("ROLE_USER");
        rolRepository.save(rolUsuario);

        crearUsuario("admin", "admin", true);
        crearUsuario("user", "user", false);
    }
}
