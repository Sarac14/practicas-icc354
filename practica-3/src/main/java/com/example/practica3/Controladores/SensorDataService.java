package com.example.practica3.Controladores;

import com.example.practica3.Entidades.SensorData;
import com.example.practica3.Repositorio.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SensorDataService {

    @Autowired
    private SensorDataRepository sensorDataRepository;

}
