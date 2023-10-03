package com.example.springbootclonemocky.controladores;

import com.example.springbootclonemocky.entidades.MockEndpoint;
import com.example.springbootclonemocky.servicios.MockEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mockendpoints")
public class MockEndpointContoller {
    @Autowired
    private MockEndpointService mockEndpointService;

    @GetMapping
    public ResponseEntity<List<MockEndpoint>> getAllMockEndpointsForUser() {
        List<MockEndpoint> mockEndpoints = mockEndpointService.findAllByCurrentUser();
        return ResponseEntity.ok(mockEndpoints);
    }

    // ... otros endpoints CRUD
}
