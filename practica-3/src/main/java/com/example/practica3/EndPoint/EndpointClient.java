package com.example.practica3.EndPoint;

import com.example.practica3.Entidades.SensorData;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class EndpointClient {

    @Autowired
    private JmsTemplate jmsTemplate;

    private Random random = new Random();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public SensorData generateRandomData(int deviceId) {
        return new SensorData(
                LocalDateTime.now().format(formatter),
                deviceId,
                (random.nextDouble() * 100),  // temperatura
                (random.nextDouble() * 100)   // humedad
        );
    }

    public void enviarMensaje(int deviceId) {
        SensorData data = generateRandomData(deviceId);

        // Convertir el objeto data a JSON y enviarlo
        jmsTemplate.convertAndSend("notificacion_sensores", data);
    }
}

