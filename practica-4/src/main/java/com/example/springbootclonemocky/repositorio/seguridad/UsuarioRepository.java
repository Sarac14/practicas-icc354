package com.example.springbootclonemocky.repositorio.seguridad;

import com.example.springbootclonemocky.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Usuario findByUsername(String username);

    Usuario findUsuarioByUsernameAndPassword(String username, String password);

}
