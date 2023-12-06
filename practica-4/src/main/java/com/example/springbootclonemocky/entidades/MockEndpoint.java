package com.example.springbootclonemocky.entidades;

import com.example.springbootclonemocky.servicios.JWTService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "Mocky")
public class MockEndpoint {


    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private String descripcion;
    private String responseCode;
    private String method;
    private String contentType;
    @Lob
    private String headers;
    @Lob
    private String responseBody;
    @DateTimeFormat
    private LocalDateTime expirationTime;
    private int responseDelay;

    private String codigo;

    private boolean seguridad;

    @ManyToOne
    Usuario user;

    public MockEndpoint(){}

    public MockEndpoint(String nombre, String descripcion, String responseCode, String method, String contentType, String headers, String responseBody, LocalDateTime expirationTime, int responseDelay, String codigo, boolean seguridad, Usuario user) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.responseCode = responseCode;
        this.method = method;
        this.contentType = contentType;
        this.headers = headers;
        this.responseBody = responseBody;
        this.expirationTime = expirationTime;
        this.responseDelay = responseDelay;
        this.codigo = codigo;
        this.user = user;

        this.seguridad = seguridad;


    }
    public boolean isSeguridad() {
        return seguridad;
    }

    public void setSeguridad(boolean seguridad) {
        this.seguridad = seguridad;
    }
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String endpointName) {
        this.nombre = endpointName;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String status) {
        this.responseCode = status;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String metodo) {
        this.method = metodo;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String content) {
        this.contentType = content;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String body) {
        this.responseBody = body;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime exp) {
        this.expirationTime = exp;
    }

    public int getResponseDelay() {
        return responseDelay;
    }

    public void setResponseDelay(int demora) {
        this.responseDelay = demora;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String expParseada(){
        DateTimeFormatter nt = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        return this.expirationTime.format(nt);
    }


}