package com.example.springbootclonemocky.entidades;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "Usuario")
public class Usuario implements Serializable {

    @Id
    private String username;
    private String password;
//    private Boolean activo;
    private String nombre;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Rol> rols;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Boolean isActivo() {
//        return activo;
//    }

//    public void setActivo(Boolean activo) {
//        this.activo = activo;
//    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Rol> getRols() {
        return rols;
    }

    public void setRols(List<Rol> rols) {
        this.rols = rols;
    }
}
