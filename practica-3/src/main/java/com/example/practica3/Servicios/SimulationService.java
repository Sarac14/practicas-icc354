package com.example.practica3.Servicios;

import com.example.practica3.EndPoint.EndpointClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SimulationService {

    @Autowired
    private EndpointClient client;

    @Scheduled(fixedRate = 60000)  // cada minuto
    public void runSimulation() {
        // Simula el dispositivo 1
        client.enviarMensaje(1);

        // Simula el dispositivo 2
        client.enviarMensaje(2);
    }
}

