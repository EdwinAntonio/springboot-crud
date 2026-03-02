package com.ingedwin.springboot.app.springboot_crud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authz -> authz
            .requestMatchers(HttpMethod.GET,"/api/users").permitAll()  //Reglas para seguridad de esa ruta dependiendo el tipo de HTTP Request
            .requestMatchers(HttpMethod.POST,"/api/users/register").permitAll() 
            .anyRequest().authenticated()) //Decimos que cualquier otro request de tipo REST aparte de esta URL, requiere autenticacion
            .csrf(config -> config.disable()) // Metodo que nos ayuda a dar una capa extra de seguridad para evitar exploits, solo que aqui se deshabilita solo para explicaciones en desarrollo
            .sessionManagement(manag -> manag.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
    }
}
