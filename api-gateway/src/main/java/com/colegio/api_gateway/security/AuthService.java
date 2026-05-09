package com.colegio.api_gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;

    private static final Map<String, String[]> USUARIOS = new HashMap<>();

    static {
        USUARIOS.put("11111111-1", new String[]{"profesor123", "PROFESOR"});
        USUARIOS.put("22222222-2", new String[]{"profesor123", "PROFESOR"});
        USUARIOS.put("33333333-3", new String[]{"alumno123",   "ALUMNO"});
        USUARIOS.put("44444444-4", new String[]{"alumno123",   "ALUMNO"});
        USUARIOS.put("12345678-9", new String[]{"admin123",    "ADMIN"});
        USUARIOS.put("55555555-5", new String[]{"apoderado123", "APODERADO"});
        USUARIOS.put("66666666-6", new String[]{"apoderado123", "APODERADO"});
    }

    public Optional<Map<String, String>> autenticar(String rut, String password) {
        String[] credenciales = USUARIOS.get(rut);

        if (credenciales == null || !credenciales[0].equals(password)) {
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