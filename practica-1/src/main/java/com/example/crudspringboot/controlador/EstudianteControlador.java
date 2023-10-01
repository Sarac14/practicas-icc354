package com.example.crudspringboot.controlador;

import com.example.crudspringboot.entidades.Estudiante;
import com.example.crudspringboot.servicios.EstudianteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteControlador {

    @Autowired
    private EstudianteServicio estudianteServicio;

    @GetMapping
    public ResponseEntity<List<Estudiante>> obtenerTodosLosEstudiantes() {
        return ResponseEntity.ok(estudianteServicio.listarEstudiantes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtenerEstudiantePorId(@PathVariable Long id) {
        return ResponseEntity.ok(estudianteServicio.obtenerUsuario(id));
    }

    @PostMapping
    public ResponseEntity<Estudiante> crearEstudiante(@RequestBody Estudiante estudiante) {
        return ResponseEntity.ok(estudianteServicio.crearEstudiante(estudiante));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizarEstudiante(@PathVariable Long id, @RequestBody Estudiante estudiante) {
        return ResponseEntity.ok(estudianteServicio.modificarEstudiante(id, estudiante));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEstudiante(@PathVariable Long id) {
        if(estudianteServicio.eliminarEstudiante(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/por-matricula/{matricula}")
    public ResponseEntity<Estudiante> obtenerEstudiantePorMatricula(@PathVariable String matricula) {
        return ResponseEntity.ok(estudianteServicio.estudiantePorMatricula(matricula));
    }
}
