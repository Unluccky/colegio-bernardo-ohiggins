package com.colegio.servicio_academico.controller;

import com.colegio.servicio_academico.model.Profesor;
import com.colegio.servicio_academico.service.ProfesorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/profesores")
@RequiredArgsConstructor
public class ProfesorController {

    private final ProfesorService service;

    @GetMapping
    public List<Profesor> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profesor> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Profesor> crear(@RequestBody Profesor profesor) {
        return ResponseEntity.ok(service.guardar(profesor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profesor> actualizar(@PathVariable Long id,
        @RequestBody Profesor datos) {
        return ResponseEntity.ok(service.actualizar(id, datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}