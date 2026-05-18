package com.colegio.api_gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String rut = request.get("rut");
        String password = request.get("password");

        Optional<Map<String, String>> resultado = authService.autenticar(rut, password);

        if (resultado.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "RUT o contraseña incorrectos"));
        }

        return ResponseEntity.ok(resultado.get());
    }
}