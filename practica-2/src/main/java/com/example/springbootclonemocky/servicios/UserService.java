package com.example.springbootclonemocky.servicios;

import com.example.springbootclonemocky.entidades.Usuario;
import com.example.springbootclonemocky.repositorio.seguridad.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService  {

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUsuario(Usuario user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = userRepository.findAll();
        return usuarios;
    }

    public Usuario findByUsername(String username) {
       return userRepository.findByUsername(username);
    }
}
