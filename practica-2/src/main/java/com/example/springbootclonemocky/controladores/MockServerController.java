package com.example.springbootclonemocky.controladores;

import com.example.springbootclonemocky.entidades.MockEndpoint;
import com.example.springbootclonemocky.servicios.MockEndpointService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.header.Header;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class MockServerController {

    @Autowired
    private MockEndpointService mockEndpointService;

    private static final Logger logger = LoggerFactory.getLogger(MockServerController.class);


    @RequestMapping("/mocked/{id}")
    public ResponseEntity<String> serveMockedEndpoint(@PathVariable String id) {
        logger.info("Entrando a serveMockedEndpoint con id: " + id);

        MockEndpoint mockEndpoint = mockEndpointService.findByUrl("/mocked/" + id);
        if (mockEndpoint == null) {
            return ResponseEntity.notFound().build();
        }

        logger.info("MockEndpoint encontrado: " + mockEndpoint.toString());  // Asegúrate de que MockEndpoint tenga un método toString() adecuado

         MediaType temp = null;
         if(mockEndpoint.getContentType().equals("text/plain")){
             temp = MediaType.TEXT_PLAIN;
         } else if(mockEndpoint.getContentType().equals("application/json")){
            temp = MediaType.APPLICATION_JSON;
        } else if(mockEndpoint.getContentType().equals("application/xml")){
            temp = MediaType.APPLICATION_XML;
        } else if(mockEndpoint.getContentType().equals("text/html")){
            temp = MediaType.TEXT_HTML;
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        Map<String, String> headersMap = mockEndpoint.getHeaders();
        for (Map.Entry<String, String> entry : headersMap.entrySet()) {
            responseHeaders.set(entry.getKey(), entry.getValue());
        }

        if (mockEndpoint.getResponseDelay() != null && mockEndpoint.getResponseDelay() > 0) {
            try {
                Thread.sleep(mockEndpoint.getResponseDelay() * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }


        logger.info("Devolviendo respuesta con código: " + mockEndpoint.getResponseCode());
        return ResponseEntity
                .status(mockEndpoint.getResponseCode())
                .headers(responseHeaders)
                .contentType(temp)
                .body(mockEndpoint.getResponseBody());
    }


}

