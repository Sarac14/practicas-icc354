package com.example.springbootclonemocky.controladores;

import com.example.springbootclonemocky.config.SecurityConfig;
import com.example.springbootclonemocky.entidades.MockEndpoint;
import com.example.springbootclonemocky.entidades.Rol;
import com.example.springbootclonemocky.entidades.Usuario;
import com.example.springbootclonemocky.repositorio.seguridad.MockEndpointRepository;
import com.example.springbootclonemocky.servicios.MockEndpointService;
import com.example.springbootclonemocky.servicios.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private SecurityConfig securityConfig;
    @Autowired
    private UserService userService;
    @GetMapping("/listarMock")
    public String getAllMockEndpointsForUser(Model model) {
        List<MockEndpoint> mockEndpoints = mockEndpointService.findAllByCurrentUser();
        model.addAttribute("lista", mockEndpoints);
        String username = securityConfig.getLoggedInUserDetails().getUsername();
        Usuario currentUser = userService.findByUsername(username);


        Set<Rol> roles = currentUser.getRoles();

        for(Rol role : roles) {
            System.out.println("-----------------------"+role.getRole()+"-----------------------");
        }

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
                                     Double expirationTime,
                                     Model model) {

        Map<String, String> headersMap = new HashMap<>();
        int size = Math.min(headerKeys.size(), headerValues.size());
        for (int i = 0; i < size; i++) {
            headersMap.put(headerKeys.get(i), headerValues.get(i));
        }
        mockEndpoint.setHeaders(headersMap);

        double expirationHoursDouble = expirationTime * 24;
        int expirationHours = (int) Math.round(expirationHoursDouble);
        mockEndpoint.setExpirationTime(expirationHours);

        String username = securityConfig.getLoggedInUserDetails().getUsername();
        Usuario currentUser = userService.findByUsername(username);
        mockEndpoint.setUser(currentUser);

        String uniqueId = UUID.randomUUID().toString();
        mockEndpoint.setUrl("/mocked/" + uniqueId);
        mockEndpoint.setUrl("/mocked/" + uniqueId);


        mockEndpointService.saveMock(mockEndpoint);

        return "redirect:/mockendpoints/listarMock";
    }

    /*@GetMapping("/display/{uuid}")
    public String displayMock(@PathVariable String uuid, Model model) {
        MockEndpoint mockEndpoint = mockEndpointRepository.findByUrl("/mocked/" + uuid);
        model.addAttribute("mockEndpoint", mockEndpoint);
        return "displayMock";
    }*/
}
