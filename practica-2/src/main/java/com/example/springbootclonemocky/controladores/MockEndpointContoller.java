package com.example.springbootclonemocky.controladores;

import com.example.springbootclonemocky.entidades.MockEndpoint;
import com.example.springbootclonemocky.repositorio.seguridad.MockEndpointRepository;
import com.example.springbootclonemocky.servicios.MockEndpointService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/mockendpoints")
public class MockEndpointContoller {
    @Autowired
    private MockEndpointService mockEndpointService;
    @Autowired
    private MockEndpointRepository mockEndpointRepository;
    //Poner si es el admin que sean todos los mocks creados
    @GetMapping("/listarMock")
    public String getAllMockEndpointsForUser(Model model) {
        List<MockEndpoint> mockEndpoints = mockEndpointService.findAllByCurrentUser();
        model.addAttribute("lista", mockEndpoints);
        return "listaMocks";
    }

    @GetMapping("/create")
    public String createMockEndpoint(Model model) {
        model.addAttribute("titulo", "Crear Nuevo Mock");
        model.addAttribute("mock", new MockEndpoint());
        return "formulario";
    }
    @PostMapping("/create")
    public String createMockEndpoint(HttpServletRequest request,
                                     @ModelAttribute MockEndpoint mockEndpoint,
                                     @RequestParam List<String> headerKeys,
                                     @RequestParam List<String> headerValues,
                                     Double expirationTime) {

        String currentUrl = request.getRequestURI();

        Map<String, String> headersMap = new HashMap<>();
        int size = Math.min(headerKeys.size(), headerValues.size());
        for (int i = 0; i < size; i++) {
            headersMap.put(headerKeys.get(i), headerValues.get(i));
        }
        mockEndpoint.setHeaders(headersMap);

        double expirationHoursDouble = expirationTime * 24;
        int expirationHours = (int) Math.round(expirationHoursDouble);
        mockEndpoint.setExpirationTime(expirationHours);
        mockEndpoint.setUrl(currentUrl);

        mockEndpointService.saveMock(mockEndpoint);
        return "redirect:/mockendpoints/listarMock";
    }



    @RequestMapping("/**")
    public ResponseEntity<String> serveMock(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        MockEndpoint mockEndpoint = mockEndpointRepository.findByPathAndMethod(path, method);
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
