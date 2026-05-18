package com.colegio.api_gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth

                // Público 
                .requestMatchers("/auth/login").permitAll()

                // Administración del sistema (solo ADMIN) 
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // Gestión de Estudiantes 
                // UTP y ADMIN: acceso total | PROFESOR: solo lectura
                .requestMatchers(
                    org.springframework.http.HttpMethod.GET,
                    "/academico/estudiantes/**"
                ).hasAnyRole("ADMIN", "UTP", "PROFESOR")

                .requestMatchers("/academico/estudiantes/**")
                    .hasAnyRole("ADMIN", "UTP")

                // Gestión de Apoderados
                .requestMatchers("/academico/apoderados/**")
                    .hasAnyRole("ADMIN", "UTP")

                // Gestión de Profesores
                .requestMatchers(
                    org.springframework.http.HttpMethod.GET,
                    "/academico/profesores/**"
                ).hasAnyRole("ADMIN", "UTP", "PROFESOR")

                .requestMatchers("/academico/profesores/**")
                    .hasAnyRole("ADMIN", "UTP")

                // Asignaturas
                .requestMatchers(
                    org.springframework.http.HttpMethod.GET,
                    "/academico/asignaturas/**"
                ).hasAnyRole("ADMIN", "UTP", "PROFESOR")

                .requestMatchers("/academico/asignaturas/**")
                    .hasAnyRole("ADMIN", "UTP")

                // Evaluaciones
                .requestMatchers(
                    org.springframework.http.HttpMethod.GET,
                    "/academico/evaluaciones/**"
                ).hasAnyRole("ADMIN", "UTP", "PROFESOR")

                .requestMatchers("/academico/evaluaciones/**")
                    .hasAnyRole("ADMIN", "UTP")

                // Notas 
                // ADMIN, UTP, PROFESOR: lectura y escritura
                // APODERADO, ALUMNO: solo lectura
                .requestMatchers(
                    org.springframework.http.HttpMethod.GET,
                    "/academico/notas/**"
                ).hasAnyRole("ADMIN", "UTP", "PROFESOR", "APODERADO", "ALUMNO")

                .requestMatchers("/academico/notas/**")
                    .hasAnyRole("ADMIN", "UTP", "PROFESOR")

                // Anotaciones de conducta 
                // ADMIN, PROFESOR: lectura y escritura | UTP: solo lectura
                .requestMatchers(
                    org.springframework.http.HttpMethod.GET,
                    "/asistencia/anotaciones/**"
                ).hasAnyRole("ADMIN", "UTP", "PROFESOR")

                .requestMatchers("/asistencia/anotaciones/**")
                    .hasAnyRole("ADMIN", "PROFESOR")

                // Asistencia 
                // ADMIN, PROFESOR: lectura y escritura
                // UTP, APODERADO, ALUMNO: solo lectura
                .requestMatchers(
                    org.springframework.http.HttpMethod.GET,
                    "/asistencia/**"
                ).hasAnyRole("ADMIN", "UTP", "PROFESOR", "APODERADO", "ALUMNO")

                .requestMatchers("/asistencia/**")
                    .hasAnyRole("ADMIN", "PROFESOR")

                // Comunicaciones 
                // ADMIN, PROFESOR, APODERADO: lectura y escritura
                // UTP y ALUMNO: sin acceso
                .requestMatchers("/comunicaciones/**")
                    .hasAnyRole("ADMIN", "PROFESOR", "APODERADO")

                // Cualquier otra ruta requiere estar autenticado 
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}