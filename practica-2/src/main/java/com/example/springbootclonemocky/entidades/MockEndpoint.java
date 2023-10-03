package com.example.springbootclonemocky.entidades;

import jakarta.persistence.*;

import java.io.Serializable;
 @Entity
public class MockEndpoint implements Serializable {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private String endpointPath;
     private String method;
     @ManyToOne
     @JoinColumn(name="user_id")
     private User user;

     public Long getId() {
         return id;
     }

     public void setId(Long id) {
         this.id = id;
     }

     public String getEndpointPath() {
         return endpointPath;
     }

     public void setEndpointPath(String endpointPath) {
         this.endpointPath = endpointPath;
     }

     public String getMethod() {
         return method;
     }

     public void setMethod(String method) {
         this.method = method;
     }

     public User getUser() {
         return user;
     }

     public void setUser(User user) {
         this.user = user;
     }
 }
