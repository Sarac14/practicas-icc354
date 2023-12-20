package com.example.crudspringboot.servicios;

import com.example.crudspringboot.entidades.Estudiante;
import com.example.crudspringboot.repositorio.EstudianteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class EstudianteServicio {
    @Autowired
    private EstudianteRepositorio estudianteRepositorio;

    public long cantidadEstudiante(){
        return estudianteRepositorio.count();
    }

    @Transactional()
    public Estudiante crearEstudiante (Estudiante estudiante){
        estudianteRepositorio.save(estudiante);
        return estudiante;
    }

    public Estudiante obtenerUsuario(Long id){
        return estudianteRepositorio.findById(id).orElseThrow(() -> {throw new RuntimeException();});
    }

    public Estudiante modificarEstudiante (Long id, Estudiante estudiante){
        Estudiante estudianteBuscado = estudianteRepositorio.getById(id);
        estudianteBuscado.setMatricula(estudiante.getMatricula());
        estudianteBuscado.setNombre(estudiante.getNombre());
        estudianteBuscado.setTelefono(estudiante.getTelefono());
        return estudianteRepositorio.save(estudianteBuscado);
    }

    public boolean eliminarEstudiante (Long id){
        try{
            estudianteRepositorio.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public List<Estudiante> listarEstudiantes() {
        List<Estudiante> estudiantes = estudianteRepositorio.findAll();
        System.out.println(estudiantes);
        return estudiantes;
    }

    public Estudiante estudiantePorMatricula(String matricula) {
        return estudianteRepositorio.consultaEstudiante(matricula);
    }

    //Optional<Estudiante> obtenerUsuario (Long id);



}
