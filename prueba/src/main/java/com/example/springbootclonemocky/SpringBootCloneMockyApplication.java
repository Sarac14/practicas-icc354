package com.example.springbootclonemocky;

import com.example.springbootclonemocky.servicios.SeguridadServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication(scanBasePackages = {"com.example.springbootclonemocky"})
public class SpringBootCloneMockyApplication {

    public static void main(String[] args) {


        ApplicationContext applicationContext = SpringApplication.run(SpringBootCloneMockyApplication.class, args);

        SeguridadServices seguridadServices = (SeguridadServices) applicationContext.getBean("seguridadServices");
        seguridadServices.crearUsuarios();

    }

}
