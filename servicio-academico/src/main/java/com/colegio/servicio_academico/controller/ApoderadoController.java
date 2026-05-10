package com.colegio.servicio_academico.controller;

import com.colegio.servicio_academico.model.Apoderado;
import com.colegio.servicio_academico.service.ApoderadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/apoderados")
@RequiredArgsConstructor
public class ApoderadoController {

    private final ApoderadoService service;

    @GetMapping
    public List<Apoderado> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apoderado> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<Apoderado> buscarPorRut(@PathVariable String rut) {
        return ResponseEntity.ok(service.buscarPorRut(rut));
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<Apoderado>> buscarPorEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(service.buscarPorEstudiante(estudianteId));
    }

    @PostMapping
    public ResponseEntity<Apoderado> crear(@RequestBody Apoderado apoderado) {
        return ResponseEntity.ok(service.guardar(apoderado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Apoderado> actualizar(@PathVariable Long id,
        @RequestBody Apoderado datos) {
        return ResponseEntity.ok(service.actualizar(id, datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}