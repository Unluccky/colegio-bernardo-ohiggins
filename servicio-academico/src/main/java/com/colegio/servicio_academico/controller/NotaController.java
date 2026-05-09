package com.colegio.servicio_academico.controller;

import com.colegio.servicio_academico.model.Nota;
import com.colegio.servicio_academico.service.NotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notas")
@RequiredArgsConstructor
public class NotaController {

    private final NotaService service;

    @GetMapping
    public List<Nota> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nota> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/estudiante/{estudianteId}")
    public List<Nota> buscarPorEstudiante(@PathVariable Long estudianteId) {
        return service.buscarPorEstudiante(estudianteId);
    }

    @PostMapping
    public ResponseEntity<Nota> crear(@RequestBody Nota nota) {
        return ResponseEntity.ok(service.guardar(nota));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}