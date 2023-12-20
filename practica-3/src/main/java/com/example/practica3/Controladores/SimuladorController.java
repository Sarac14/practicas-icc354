package com.example.practica3.Controladores;

import com.example.practica3.EndPoint.EndpointClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimuladorController {

    @Autowired
    private EndpointClient endpointClient;

    @GetMapping("/simulate/{deviceId}")
    public ResponseEntity<String> simulateSensorData(@PathVariable int deviceId) {
        endpointClient.enviarMensaje(deviceId);
        return ResponseEntity.ok("Data simulated and sent for device: " + deviceId);
    }
}
