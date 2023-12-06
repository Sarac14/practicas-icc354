package com.example.springbootclonemocky.controladores;

import com.example.springbootclonemocky.entidades.MockEndpoint;
import com.example.springbootclonemocky.servicios.JWTService;
import com.example.springbootclonemocky.servicios.MockEndpointService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
public class MockServerController {
    @Autowired
    MockEndpointService mockS;
    @Autowired
    JWTService jwts;
    @RequestMapping("/{name}/{codigo}")
    public ResponseEntity<String> consulta(
            @PathVariable() String name,
            @PathVariable() String codigo,
            @RequestParam("token") String token
    ){
        MockEndpoint omock = mockS.buscarCodigoAndNombre(name,codigo);
        if(omock.isSeguridad()) {
            if(!jwts.generateJwt(omock).equals(token)){
                HttpHeaders headers = new HttpHeaders();
                headers.add("Location", "/mockendpoints/");
                return new ResponseEntity<>(headers, HttpStatus.FOUND);
            }
        }
        if ((LocalDateTime.now()).isBefore(omock.getExpirationTime())) {
            MediaType temp = null;
            if (omock.getContentType().equals("text/plain")) {
                temp = MediaType.TEXT_PLAIN;
            } else if (omock.getContentType().equals("text/html")) {
                temp = MediaType.TEXT_HTML;
            } else if (omock.getContentType().equals("application/json")) {
                temp = MediaType.APPLICATION_JSON;
            } else if (omock.getContentType().equals("application/xml")) {
                temp = MediaType.APPLICATION_XML;
            }

            HttpHeaders headers = new HttpHeaders();
            if (!omock.getHeaders().isEmpty()) {
                JSONObject headersJson = new JSONObject(omock.getHeaders());
                for (String key : headersJson.keySet()) {
                    headers.add(key, headersJson.getString(key));
                }
            }

            try {
                Thread.sleep(omock.getResponseDelay() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (omock.getResponseBody().isEmpty()) {
                return ResponseEntity.status(Integer.parseInt(omock.getResponseCode())).contentType(temp).headers(headers).body("{}");
            } else {
                return ResponseEntity.status(Integer.parseInt(omock.getResponseCode())).contentType(temp).headers(headers).body(omock.getResponseBody());
            }

        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/mockendpoints/");
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }


    }

}
