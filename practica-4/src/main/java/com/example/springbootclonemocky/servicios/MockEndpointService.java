package com.example.springbootclonemocky.servicios;

import com.example.springbootclonemocky.entidades.MockEndpoint;
import com.example.springbootclonemocky.entidades.Usuario;
import com.example.springbootclonemocky.repositorio.seguridad.MockEndpointRepository;
import com.example.springbootclonemocky.repositorio.seguridad.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MockEndpointService {
    @Autowired
    private MockEndpointRepository repo;

    @Transactional
    public MockEndpoint crearMockup(MockEndpoint mock){
        repo.save(mock);
        return mock;
    }
    @Transactional
    public List<MockEndpoint> buscarTodo(){
        return repo.findAll();
    }
    @Transactional
    public List<MockEndpoint> buscarTodoByUsuario(Usuario ususario){
        return repo.findAllByUser(ususario);
    }
    @Transactional
    public MockEndpoint buscarMockById(Long id){
        Optional<MockEndpoint> mcc= repo.findById(id);
        return mcc.orElse(null);
    }
    @Transactional
    public void eliminarMockById(Long id){
        repo.deleteById(id);
    }
    @Transactional
    public MockEndpoint buscarCodigoAndNombre(String nombre, String codigo){
        Optional<MockEndpoint> mcc= repo.findByCodigoAndNombre(codigo,nombre);
        return mcc.orElse(null);
    }


}
