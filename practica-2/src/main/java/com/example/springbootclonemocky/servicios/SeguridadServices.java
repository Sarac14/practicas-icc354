package com.example.springbootclonemocky.servicios;

import com.example.springbootclonemocky.entidades.Rol;
import com.example.springbootclonemocky.entidades.User;
import com.example.springbootclonemocky.repositorio.RolRepository;
import com.example.springbootclonemocky.repositorio.UserRepository;
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
public class SeguridadServices implements UserDetailsService  {
    private UserRepository usuarioRepository;
    private RolRepository rolRepository;
    private PasswordEncoder passwordEncoder;

    public SeguridadServices(UserRepository usuarioRepository, RolRepository rolRepository) {
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
        Rol rolAdmin = new Rol("ROLE_ADMIN");
        //Rol rolUsuario = new Rol("ROLE_USER");
        rolRepository.save(rolAdmin);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
       // admin.setNombre("Administrador");
        //admin.setActivo(true);
        admin.setRoles(new HashSet<>(Arrays.asList(rolAdmin)));
        usuarioRepository.save(admin);

       /* Usuario user = new Usuario();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setNombre("Usuario");
        user.setActivo(true);
        user.setRoles(new HashSet<>(Arrays.asList(rolUsuario)));
        usuarioRepository.save(user);*/
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Autenticación JPA");
        User user = usuarioRepository.findByUsuario(username);
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
