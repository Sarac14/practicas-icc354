package com.example.springbootclonemocky.servicios;

import com.example.springbootclonemocky.entidades.MockEndpoint;
import com.example.springbootclonemocky.entidades.Usuario;
import com.example.springbootclonemocky.repositorio.seguridad.MockEndpointRepository;
import com.example.springbootclonemocky.repositorio.seguridad.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MockEndpointService {

    @Autowired
    private MockEndpointRepository mockEndpointRepository;

    @Autowired
    private UsuarioRepository userRepository;

    public List<MockEndpoint> findAllByCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Usuario user = userRepository.findByUsername(auth.getName()).orElse(null);
        Usuario user = userRepository.findByUsername(auth.getName());

        if(user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return mockEndpointRepository.findAll();
        } else {
            return mockEndpointRepository.findAllByUser(user);
        }
    }

    //otros metodos del CRUD
}
