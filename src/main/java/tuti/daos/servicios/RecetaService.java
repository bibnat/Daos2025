package tuti.daos.servicios;

import tuti.daos.entidades.Receta;
import java.util.List;

public interface RecetaService {

    Receta crear(Receta r);

    Receta buscarPorId(Integer id);

    List<Receta> listar();

    Receta actualizar(Integer id, Receta datos);

    void eliminar(Integer id);
}
