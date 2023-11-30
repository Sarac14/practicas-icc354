package com.example.springbootclonemocky.entidades;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Rol")
public class Rol implements Serializable {
    @Id
    private String role;

    public Rol(){

    }

    public Rol(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
