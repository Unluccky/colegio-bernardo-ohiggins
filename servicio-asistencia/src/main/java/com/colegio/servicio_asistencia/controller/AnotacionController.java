package com.colegio.servicio_asistencia.controller;

import com.colegio.servicio_asistencia.model.Anotacion;
import com.colegio.servicio_asistencia.service.AnotacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/anotaciones")
@RequiredArgsConstructor
public class AnotacionController {

    private final AnotacionService service;

    @GetMapping
    public List<Anotacion> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anotacion> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/estudiante/{estudianteId}")
    public List<Anotacion> buscarPorEstudiante(@PathVariable Long estudianteId) {
        return service.buscarPorEstudiante(estudianteId);
    }

    @PostMapping
    public ResponseEntity<Anotacion> crear(@RequestBody Anotacion anotacion) {
        return ResponseEntity.ok(service.guardar(anotacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Anotacion> actualizar(@PathVariable Long id,
        @RequestBody Anotacion datos) {
        return ResponseEntity.ok(service.actualizar(id, datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}