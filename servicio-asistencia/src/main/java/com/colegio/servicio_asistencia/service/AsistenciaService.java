package com.colegio.servicio_asistencia.service;

import com.colegio.servicio_asistencia.model.Asistencia;
import java.util.List;

public interface AsistenciaService {
    List<Asistencia> listarTodos();
    Asistencia buscarPorId(Long id);
    List<Asistencia> buscarPorEstudiante(Long estudianteId);
    Asistencia guardar(Asistencia asistencia);
    Asistencia actualizar(Long id, Asistencia asistencia);
    void eliminar(Long id);
}