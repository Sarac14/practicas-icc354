package com.example.springbootclonemocky.repositorio.seguridad;

import com.example.springbootclonemocky.entidades.MockEndpoint;
import com.example.springbootclonemocky.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MockEndpointRepository extends JpaRepository<MockEndpoint, Long> {
    List<MockEndpoint> findAll();
    List<MockEndpoint> findAllByUser(Usuario user);
    void deleteById(Long id);

    Optional<MockEndpoint> findByCodigoAndNombre(String codigo, String nombre);

    Optional<MockEndpoint> findById(Long id);

}