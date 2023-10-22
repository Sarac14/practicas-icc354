package com.example.practica3.EndPoint;

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

    public void enviarMensaje() {
        // Generar valores aleatorios de temperatura y humedad
        String fechaGeneracion = obtenerFechaGeneracion(); // Implementa la lógica para obtener la fecha de generación
        int idDispositivo = obtenerIdDispositivo(); // Implementa la lógica para obtener el ID del dispositivo
        double temperatura = generarTemperaturaAleatoria(); // Implementa la lógica para generar la temperatura aleatoria
        double humedad = generarHumedadAleatoria(); // Implementa la lógica para generar la humedad aleatoria

        // Crear la trama JSON
        String jsonTrama = "{\"fechaGeneracion\": \"" + fechaGeneracion + "\", \"IdDispositivo\": " + idDispositivo +
                ", \"temperatura\": " + temperatura + ", \"humedad\": " + humedad + "}";

        // Enviar el mensaje a la cola de notificación
        jmsTemplate.convertAndSend("notificacion_sensores", jsonTrama);
    }

    // Métodos para la generación de datos aleatorios
    private int obtenerIdDispositivo() {
        Random random = new Random();
        return random.nextInt(1000); // Genera un ID aleatorio en el rango de 0 a 999 (puedes ajustar el rango según tus necesidades)
    }
    private double generarTemperaturaAleatoria() {
        // Genera un valor de temperatura aleatorio en el rango de 0 a 100 grados Celsius
        Random random = new Random();
        return random.nextDouble() * 100;
    }

    // Método para generar humedad aleatoria
    private double generarHumedadAleatoria() {
        // Genera un valor de humedad aleatorio en el rango de 0 a 100 por ciento
        Random random = new Random();
        return random.nextDouble() * 100;
    }

    private String obtenerFechaGeneracion() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return myDateObj.format(myFormatObj);
    }

}
