package com.example.crudspringboot.repositorio;

import com.example.crudspringboot.entidades.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepositorio extends JpaRepository<Estudiante, Long> {
    Estudiante findByNombre(String nombre);

    @Query("select u from Estudiante u where u.matricula = ?1")
    Estudiante consultaEstudiante(String matricula);
}
