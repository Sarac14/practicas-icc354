package com.example.springbootclonemocky.servicios;

import com.example.springbootclonemocky.entidades.Rol;
import com.example.springbootclonemocky.entidades.Usuario;
import com.example.springbootclonemocky.repositorio.RolRepository;
import com.example.springbootclonemocky.repositorio.seguridad.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Qualifier
public class SeguridadServices implements UserDetailsService  {
    private UsuarioRepository usuarioRepository;
    private RolRepository rolRepository;
    private PasswordEncoder passwordEncoder;

    //@Autowired
    public SeguridadServices(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        passwordEncoder = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        return passwordEncoder;
    }

    public void crearUsuarios(){

        System.out.println("Creación del usuario y rol en la base de datos");


        //Rol rolAdmin = new Rol("ROLE_ADMIN");
        Rol rolAdmin = rolRepository.findByRole("ROLE_ADMIN");
        if (rolAdmin == null) {
            rolAdmin = new Rol("ROLE_ADMIN");
            rolRepository.save(rolAdmin);
        }
        //rolRepository.save(rolAdmin);

       Rol rolUsuario = new Rol("ROLE_USER");
        //rolRepository.save(rolUsuario);



        Usuario admin = new Usuario();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(new HashSet<>(Arrays.asList(rolAdmin)));
        usuarioRepository.save(admin);

        /*Usuario user = new Usuario();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setNombre("Usuario");
        user.setActivo(true);
        user.setRols(new HashSet<>(Arrays.asList(rolUsuario)));
        usuarioRepository.save(user);*/
    }





    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Autenticación JPA");
        Usuario user = usuarioRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("Usuario no existe.");
        }

        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Rol role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, grantedAuthorities);
    }
}
