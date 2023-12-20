package com.example.practica3.EndPoint;
import com.example.practica3.Entidades.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class SensorMessageConsumer {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @JmsListener(destination = "notificacion_sensores")
    public void receiveMessage(SensorData sensorData) {
        simpMessagingTemplate.convertAndSend("/topic/sensor-data", sensorData);
    }
}
