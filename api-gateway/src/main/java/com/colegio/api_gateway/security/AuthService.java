package com.colegio.api_gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Mapa de usuarios: RUT → { passwordHash, ROL }
     *
     * Contraseñas en texto plano originales (para referencia de pruebas):
     *   profesor123  → PROFESOR
     *   alumno123    → ALUMNO
     *   admin123     → ADMIN
     *   apoderado123 → APODERADO
     *   utp123       → UTP
     *
     * Los hashes se generan UNA sola vez al cargar la clase (bloque static).
     * En producción real estos vendrían de una base de datos.
     */
    private static final Map<String, String[]> USUARIOS = new HashMap<>();

    static {
        // --- PROFESORES ---
        USUARIOS.put("11111111-1", new String[]{
                new BCryptPasswordEncoder().encode("profesor123"), "PROFESOR"});
        USUARIOS.put("22222222-2", new String[]{
                new BCryptPasswordEncoder().encode("profesor123"), "PROFESOR"});

        // --- ALUMNOS ---
        USUARIOS.put("33333333-3", new String[]{
                new BCryptPasswordEncoder().encode("alumno123"), "ALUMNO"});
        USUARIOS.put("44444444-4", new String[]{
                new BCryptPasswordEncoder().encode("alumno123"), "ALUMNO"});

        // --- ADMIN ---
        USUARIOS.put("12345678-9", new String[]{
                new BCryptPasswordEncoder().encode("admin123"), "ADMIN"});

        // --- APODERADOS ---
        USUARIOS.put("55555555-5", new String[]{
                new BCryptPasswordEncoder().encode("apoderado123"), "APODERADO"});
        USUARIOS.put("66666666-6", new String[]{
                new BCryptPasswordEncoder().encode("apoderado123"), "APODERADO"});

        // --- UTP ---
        USUARIOS.put("77777777-7", new String[]{
                new BCryptPasswordEncoder().encode("utp123"), "UTP"});
        USUARIOS.put("88888888-8", new String[]{
                new BCryptPasswordEncoder().encode("utp123"), "UTP"});
    }

    public Optional<Map<String, String>> autenticar(String rut, String password) {
        String[] credenciales = USUARIOS.get(rut);

        // Verificar existencia del usuario y contraseña con BCrypt
        if (credenciales == null || !passwordEncoder.matches(password, credenciales[0])) {
            return Optional.empty();
        }

        String token = jwtUtil.generateToken(rut, credenciales[1]);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("token", token);
        respuesta.put("rut", rut);
        respuesta.put("role", credenciales[1]);

        return Optional.of(respuesta);
    }
}