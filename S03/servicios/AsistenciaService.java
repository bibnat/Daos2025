package tuti.daos.servicios;

import tuti.daos.entidades.Asistencia;
import tuti.daos.presentacion.dto.AsistenciaDTO;

import java.util.List;

public interface AsistenciaService {

    List<Asistencia> listar();
    Asistencia buscarPorId(Integer id);
    List<Asistencia> listarPorAsistido(Integer idAsistido);

    Asistencia crearDesdeDTO(AsistenciaDTO dto);

    void eliminar(Integer id);
}
