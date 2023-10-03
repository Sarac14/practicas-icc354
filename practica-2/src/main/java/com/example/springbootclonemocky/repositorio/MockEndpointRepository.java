package com.example.springbootclonemocky.repositorio;

import com.example.springbootclonemocky.entidades.MockEndpoint;
import com.example.springbootclonemocky.entidades.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MockEndpointRepository extends JpaRepository<MockEndpoint, Long> {
    @Query("select u from MockEndpoint u where u.id = ?1")
    MockEndpoint consultaEndpoint(Long id);

    List<MockEndpoint> findAllByUser(User user);
}
