package com.example.springbootclonemocky.repositorio;

import com.example.springbootclonemocky.entidades.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUser(String username);

    @Query("select u from User u where u.id = ?1")
    User consultaUserPorId(Long id);

    @Query("select u from User u where u.username = ?1")
    User consultaUserPorUsername(String username);

    Optional<User> findByUsername(String username);

    User findByUsuario(String username);
}
