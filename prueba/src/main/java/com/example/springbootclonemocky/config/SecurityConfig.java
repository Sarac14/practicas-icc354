package com.example.springbootclonemocky.config;

import com.example.springbootclonemocky.entidades.Usuario;
import com.example.springbootclonemocky.servicios.SeguridadServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private DataSource dataSource;
    private  SeguridadServices seguridadServices;
    private  PasswordEncoder passwordEncoder;

    public SecurityConfig(DataSource dataSource, SeguridadServices seguridadServices, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.seguridadServices = seguridadServices;
        this.passwordEncoder = passwordEncoder;

    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth =
                http.getSharedObject(AuthenticationManagerBuilder.class);


        System.out.println("Autentificación en JPA");
        auth.userDetailsService(seguridadServices)
                .passwordEncoder(passwordEncoder);


        return auth.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {

       /* http.authorizeRequests()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/user/register")).permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/user/listarUsuarios")).permitAll();
        /*http.authorizeHttpRequests(authorization ->
                authorization
                        .requestMatchers("/user/login", "/user/register").permitAll()
                        .anyRequest().authenticated()
        );*/

     http.authorizeHttpRequests(authorization ->
                       authorization
                               .requestMatchers(mvc.pattern("/")).permitAll()
                               .requestMatchers(AntPathRequestMatcher.antMatcher("/css/**"), AntPathRequestMatcher.antMatcher("/js/**"), AntPathRequestMatcher.antMatcher("/webjars/**"), AntPathRequestMatcher.antMatcher("*.html")).permitAll()
                               //.requestMatchers(mvc.pattern("/h2-console/**")).permitAll()
                               .requestMatchers(AntPathRequestMatcher.antMatcher("/api-docs/**"), AntPathRequestMatcher.antMatcher("/api-docs.yaml"), AntPathRequestMatcher.antMatcher("/swagger-ui.html"), AntPathRequestMatcher.antMatcher("/swagger-ui/**")).permitAll()
                               .requestMatchers(AntPathRequestMatcher.antMatcher("/mockendpoints/**")).authenticated()
                               .requestMatchers(AntPathRequestMatcher.antMatcher("/admin/")).hasAnyRole("ADMIN", "USER")
                               .requestMatchers(AntPathRequestMatcher.antMatcher("/user/**")).hasAnyRole("ADMIN")
                               .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                               .anyRequest().authenticated()
               )

               .formLogin((form) -> form
                       .loginPage("/user/login")
                       .failureUrl("/login?error")
                       .defaultSuccessUrl("/")
                       .permitAll()
               )
               .logout((logout) -> logout
                       .logoutSuccessUrl("/")
                       .invalidateHttpSession(true)
                       .logoutRequestMatcher(AntPathRequestMatcher.antMatcher("/user/logout"))
                       .deleteCookies("JSESSIONID")
                       .permitAll());


        return http.build();
    }

    public UserDetails getLoggedInUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }




}
