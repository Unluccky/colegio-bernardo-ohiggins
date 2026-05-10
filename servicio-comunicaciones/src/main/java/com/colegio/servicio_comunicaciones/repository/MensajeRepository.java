package com.colegio.servicio_comunicaciones.repository;

import com.colegio.servicio_comunicaciones.model.Mensaje;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MensajeRepository extends MongoRepository<Mensaje, String> {
    List<Mensaje> findByDestinatarioId(Long destinatarioId);
    List<Mensaje> findByRemitenteId(Long remitenteId);
}