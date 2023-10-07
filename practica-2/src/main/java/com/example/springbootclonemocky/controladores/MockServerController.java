package com.example.springbootclonemocky.controladores;

import com.example.springbootclonemocky.entidades.MockEndpoint;
import com.example.springbootclonemocky.servicios.MockEndpointService;
import jakarta.servlet.http.HttpServletRequest;
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

@Controller
@RequestMapping
public class MockServerController {

    @Autowired
    private MockEndpointService mockEndpointService;

    @RequestMapping("/mocked/{id}")
    public ResponseEntity<String> serveMockedEndpoint(@PathVariable String id) {
        MockEndpoint mockEndpoint = mockEndpointService.findByUrl("/mocked/" + id);
        if (mockEndpoint == null) {
            return ResponseEntity.notFound().build();
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

        return ResponseEntity.status(mockEndpoint.getResponseCode())
                .headers(responseHeaders)
                .contentType(MediaType.parseMediaType(mockEndpoint.getContentType()))
                .body(mockEndpoint.getResponseBody());
    }


}

