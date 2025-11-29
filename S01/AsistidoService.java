package tuti.daos.servicios;

import tuti.daos.entidades.Asistido;
import tuti.daos.presentacion.dto.AsistidoDTO;

import java.util.List;

public interface AsistidoService {

    List<Asistido> listar();

    Asistido buscarPorId(Integer id);

    void eliminar(Integer id);

    // Métodos para manejo mediante DTO
    Asistido crearDesdeDTO(AsistidoDTO dto);

    Asistido actualizarDesdeDTO(Integer id, AsistidoDTO dto);
}
