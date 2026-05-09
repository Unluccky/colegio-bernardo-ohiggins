package com.colegio.servicio_comunicaciones.service.impl;

import com.colegio.servicio_comunicaciones.model.Mensaje;
import com.colegio.servicio_comunicaciones.repository.MensajeRepository;
import com.colegio.servicio_comunicaciones.service.MensajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MensajeServiceImpl implements MensajeService {

    private final MensajeRepository repo;

    @Override
    public List<Mensaje> listarTodos() {
        return repo.findAll();
    }

    @Override
    public Mensaje buscarPorId(String id) {
        return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Mensaje no encontrado: " + id));
    }

    @Override
    public List<Mensaje> buscarPorDestinatario(Long destinatarioId) {
        return repo.findByDestinatarioId(destinatarioId);
    }

    @Override
    public Mensaje guardar(Mensaje mensaje) {
        return repo.save(mensaje);
    }

    @Override
    public void eliminar(String id) {
        repo.deleteById(id);
    }
}