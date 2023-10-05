package com.example.springbootclonemocky.repositorio.seguridad;

import com.example.springbootclonemocky.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
   // Usuario findByUser(String username);

    @Query("select u from Usuario u where u.id = ?1")
    Usuario consultaUserPorId(Long id);

    @Query("select u from Usuario u where u.username = ?1")
    Usuario consultaUserPorUsername(String username);


    Usuario findByUsername(String username);

}
