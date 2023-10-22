package com.example.practica3.Conf;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.connection.CachingConnectionFactory;


@Configuration
@EnableJms
public class JmsConfig {

    // Configuración de la conexión con el servidor de mensajería
    @Bean
    public ConnectionFactory connectionFactory() {
        // Configuración específica de ActiveMQ
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616"); // URL del servidor de ActiveMQ
        connectionFactory.setUserName("admin"); // Nombre de usuario si es necesario
        connectionFactory.setPassword("admin"); // Contraseña si es necesario
        return connectionFactory;
    }

    // Configuración del convertidor de mensajes
    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    // Configuración del JmsTemplate
    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setMessageConverter(jacksonJmsMessageConverter());
        return template;
    }
}