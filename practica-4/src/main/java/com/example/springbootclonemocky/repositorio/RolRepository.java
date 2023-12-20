package com.example.springbootclonemocky.repositorio;

import com.example.springbootclonemocky.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RolRepository extends JpaRepository<Rol,String> {

    Rol findByRole(String role);
}
