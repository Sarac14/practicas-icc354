package com.example.springbootclonemocky.controladores;

import com.example.springbootclonemocky.entidades.MockEndpoint;
import com.example.springbootclonemocky.entidades.Usuario;
import com.example.springbootclonemocky.servicios.JWTService;
import com.example.springbootclonemocky.servicios.MockEndpointService;
import com.example.springbootclonemocky.servicios.SeguridadServices;
import com.example.springbootclonemocky.servicios.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
@RequestMapping("/mockendpoints")
public class MockEndpointContoller {
    @Autowired
    JWTService sjwt;
    @Autowired
    MockEndpointService mock;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    SeguridadServices seguridadServices;
    @Autowired
    UserService userService;


    @GetMapping("/listarMock")
    public String listado(Model model){
        // model.addAttribute("titulo", "CRUD Mocky");
        Usuario usuario = seguridadServices.getAuthorizedUser();
        if((usuario.getRols().get(0).getRole().equalsIgnoreCase("ROLE_ADMIN") )){
            model.addAttribute("lista", mock.buscarTodo());
        }else{
            model.addAttribute("lista", mock.buscarTodoByUsuario(usuario));
        }


        model.addAttribute("jwt",sjwt);

        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        String baseUrl = scheme + "://" + serverName + ":" + serverPort+"/api/";
        model.addAttribute("url",baseUrl);
        return "listaMocks";
    }
    @GetMapping("/crear")
    public String crearmodel(Model model){
        //System.out.println("El id es:"+id);
        //
        List<String> status = List.of("100","101","102",
                "200","201","202","203","204","205","206","207",
                "208","226", "300","301","302","303","304","305",
                "307","308", "400","401","402","403","404","405",
                "406","407","408","409","410","411","412","413",
                "414","415","416","417","418","421","422","423",
                "424","426","428","429","431","451","500","501",
                "502","503","504","505","506","507","508","510",
                "511");
        List<String> metodos = List.of("GET","POST","PUT","PATH","DELETE","HEAD","OPTIONS");
        List<String> listExp = List.of("minuto","hora", "dia", "semana", "mes", "año");
        List<String> listDem = List.of("0","1","5","10","30","40","60");
        List<String> content = List.of("text/plain","text/html","application/json","application/xml");
        model.addAttribute("headerplace","{\n" +
                "  \"saludo\": \"Hola\",\n" +
                "  \"mensaje\": \"¡Bienvenido al mundo de los encabezados!\"\n" +
                "}");
        model.addAttribute("bodyplace","{\n" +
                "  \"saludo\": \"Hola\",\n" +
                "  \"mensaje\": \"¡Bienvenido al mundo de los body!\"\n" +
                "}");
        model.addAttribute("titulo", "Crea tu Mock");
        model.addAttribute("visualizar", "false");
        model.addAttribute("listStatus", status);
        model.addAttribute("metodo", metodos);
        model.addAttribute("listExp", listExp);
        model.addAttribute("selectexp","año");
        model.addAttribute("listDem", listDem);
        model.addAttribute("content", content);
        model.addAttribute("accion","/mockendpoints/creacion");
        model.addAttribute("selectmet", "GET");
        model.addAttribute("selectstat", "200");
        model.addAttribute("conselect","application/json");
        model.addAttribute("tipo", "crear");

        return "formulario";
    }

    @PostMapping("/creacion")
    public String crearMocky(@RequestParam("status") String status,@RequestParam("metodo") String metodo,
                             @RequestParam("content") String content,@RequestParam("nombre") String nombre,
                             @RequestParam("desc") String desc,@RequestParam("headers") String headers,
                             @RequestParam("body") String body,@RequestParam("exp") String exp,@RequestParam("dem") int dem, @RequestParam("seguridadJwt") boolean sec ){
        LocalDateTime fechaActual = LocalDateTime.now();
        if(exp.equals("minuto")){
            fechaActual = fechaActual.plus(1, ChronoUnit.MINUTES);
        }else if(exp.equals("hora")){
            fechaActual = fechaActual.plus(1, ChronoUnit.HOURS);
        } else if (exp.equals("dia")) {
            System.out.println("dia");
            fechaActual = fechaActual.plusDays(1);

        } else if (exp.equals("semana")) {
            System.out.println("semana");
            fechaActual = fechaActual.plusWeeks(1);

        } else if (exp.equals("mes")) {
            System.out.println("mes");
            fechaActual = fechaActual.plusMonths(1);

        } else if (exp.equals("año")) {
            System.out.println("año");
            fechaActual = fechaActual.plusYears(1);
        }
        UUID uuid = UUID.randomUUID();
        System.out.println(fechaActual);
        Usuario usuario = seguridadServices.getAuthorizedUser();
        MockEndpoint temp = new MockEndpoint(nombre,desc,status,metodo,content,headers,body,fechaActual,dem,uuid.toString().replaceAll("[^a-zA-Z0-9]", ""),sec,usuario) ;
        mock.crearMockup(temp);

        return "redirect:/mockendpoints/listarMock";
    }

    @GetMapping("/editar/{id}")
    public String editar(Model model,@PathVariable()  long id){
        MockEndpoint mm = mock.buscarMockById(id);
        List<String> status = List.of("100","101","102",
                "200","201","202","203","204","205","206","207",
                "208","226", "300","301","302","303","304","305",
                "307","308", "400","401","402","403","404","405",
                "406","407","408","409","410","411","412","413",
                "414","415","416","417","418","421","422","423",
                "424","426","428","429","431","451","500","501",
                "502","503","504","505","506","507","508","510",
                "511");
        List<String> metodos = List.of("GET","POST","PUT","PATH","DELETE","HEAD","OPTIONS");
        List<String> listExp = List.of("minuto","hora", "dia", "semana", "mes", "año");
        List<String> listDem = List.of("0","1","5","10","30","40","60");
        List<String> content = List.of("text/plain","text/html","application/json","application/xml");
        model.addAttribute("headerplace","{\n" +
                "  \"saludo\": \"Hola\",\n" +
                "  \"mensaje\": \"¡Bienvenido al mundo de los encabezados!\"\n" +
                "}");
        model.addAttribute("bodyplace","{\n" +
                "  \"saludo\": \"Hola\",\n" +
                "  \"mensaje\": \"¡Bienvenido al mundo de los body!\"\n" +
                "}");
        model.addAttribute("titulo", "Editar");
        model.addAttribute("visualizar", "false");
        model.addAttribute("listStatus", status);
        model.addAttribute("metodo", metodos);
        model.addAttribute("listExp", listExp);
        model.addAttribute("listDem", listDem);
        model.addAttribute("content", content);
        model.addAttribute("accion","/mockendpoints/modificar/"+mm.getId());
        model.addAttribute("selectexp","año");
        model.addAttribute("selectmet", mm.getMethod());
        model.addAttribute("selectstat", mm.getResponseCode());
        model.addAttribute("conselect",mm.getContentType());
        model.addAttribute("tipo", "editar");
        model.addAttribute("fname", mm.getNombre());
        model.addAttribute("fdesc", mm.getDescripcion());
        model.addAttribute("fheaders", mm.getHeaders());
        model.addAttribute("fbody", mm.getResponseBody());
        model.addAttribute("selecdem",mm.getResponseDelay());

        return "formulario";
    }
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable()  long id,@RequestParam("status") String status,@RequestParam("metodo") String metodo,
                            @RequestParam("content") String content,@RequestParam("nombre") String nombre,
                            @RequestParam("desc") String desc,@RequestParam("headers") String headers,
                            @RequestParam("body") String body,@RequestParam("exp") String exp,@RequestParam("dem") int dem,@RequestParam("seguridadJwt") boolean sec ){
        LocalDateTime fechaActual = LocalDateTime.now();
        if(exp.equals("minuto")){
            fechaActual = fechaActual.plus(1, ChronoUnit.MINUTES);
        }else if(exp.equals("hora")){
            fechaActual = fechaActual.plus(1, ChronoUnit.HOURS);
        } else if (exp.equals("dia")) {
            System.out.println("dia");
            fechaActual = fechaActual.plusDays(1);

        } else if (exp.equals("semana")) {
            System.out.println("semana");
            fechaActual = fechaActual.plusWeeks(1);

        } else if (exp.equals("mes")) {
            System.out.println("mes");
            fechaActual = fechaActual.plusMonths(1);

        } else if (exp.equals("año")) {
            System.out.println("año");
            fechaActual = fechaActual.plusYears(1);
        }
        System.out.println(fechaActual);
        MockEndpoint temp =mock.buscarMockById(id);
        temp.setNombre(nombre);
        temp.setDescripcion(desc);
        temp.setResponseCode(status);
        temp.setMethod(metodo);
        temp.setContentType(content);
        temp.setHeaders(headers);
        temp.setResponseBody(body);
        temp.setExpirationTime(fechaActual);
        temp.setResponseDelay(dem);
        temp.setSeguridad(sec);
        temp.setCodigo(mock.buscarMockById(id).getCodigo());
        mock.crearMockup(temp);

        return "redirect:/mockendpoints/listarMock";
    }
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable()  long id){
        mock.eliminarMockById(id);
        return "redirect:/mockendpoints/listarMock";
    }

}
