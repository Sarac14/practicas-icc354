package com.example.practica3.Controladores;

import com.example.practica3.Entidades.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@MessageMapping("/sensores")
public class SensorDataController {

    @Autowired
    private SensorDataService sensorDataService;

    @GetMapping("/graficos")
    public String mostrarGraficos() {
        return "/sensores/dataSensor"; // Nombre de la vista de la página que muestra los gráficos
    }

}