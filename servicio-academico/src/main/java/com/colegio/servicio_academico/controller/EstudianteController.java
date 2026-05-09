package com.colegio.servicio_academico.controller;

import com.colegio.servicio_academico.model.Estudiante;
import com.colegio.servicio_academico.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final EstudianteService service;

    @GetMapping
    public List<Estudiante> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<Estudiante> buscarPorRut(@PathVariable String rut) {
        return ResponseEntity.ok(service.buscarPorRut(rut));
    }

    @PostMapping
    public ResponseEntity<Estudiante> crear(@RequestBody Estudiante estudiante) {
        return ResponseEntity.ok(service.guardar(estudiante));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizar(@PathVariable Long id,
        @RequestBody Estudiante datos) {
        return ResponseEntity.ok(service.actualizar(id, datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}