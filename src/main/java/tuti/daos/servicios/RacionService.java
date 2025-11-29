package tuti.daos.servicios;

import tuti.daos.entidades.Racion;
import tuti.daos.presentacion.dto.RacionDTO;

import java.util.List;

public interface RacionService {

    List<Racion> listar();

    Racion buscarPorId(Integer id);

    void eliminar(Integer id);

    // Métodos para manejo mediante DTO
    Racion crearDesdeDTO(RacionDTO dto);

    Racion actualizarDesdeDTO(Integer id, RacionDTO dto);
}
