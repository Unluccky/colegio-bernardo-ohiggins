package com.colegio.servicio_comunicaciones.service;

import com.colegio.servicio_comunicaciones.model.Mensaje;
import java.util.List;

public interface MensajeService {
    List<Mensaje> listarTodos();
    Mensaje buscarPorId(String id);
    List<Mensaje> buscarPorDestinatario(Long destinatarioId);
    Mensaje guardar(Mensaje mensaje);
    void eliminar(String id);
}