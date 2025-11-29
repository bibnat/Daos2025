package tuti.daos.servicios.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tuti.daos.accesoDatos.RecetaRepository;
import tuti.daos.entidades.Receta;
import tuti.daos.excepciones.*;
import tuti.daos.servicios.RecetaService;

import java.util.List;

@Service
@Transactional
public class RecetaServiceImpl implements RecetaService {

    private final RecetaRepository repo;

    public RecetaServiceImpl(RecetaRepository repo) {
        this.repo = repo;
    }

    @Override
    public Receta crear(Receta r) {

        // No permitir ID en POST
        if (r.getId() != null)
            throw new IllegalArgumentException("No se permite enviar ID en POST.");

        // Nombre único
        if (repo.existsByNombre(r.getNombre()))
            throw new RecursoDuplicadoException("Ya existe una receta con ese nombre.");

        return repo.save(r);
    }

    @Override
    public Receta buscarPorId(Integer id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Receta no encontrada: " + id)
                );
    }

    @Override
    public List<Receta> listar() {
        return repo.findAll();
    }

    @Override
    public Receta actualizar(Integer id, Receta datos) {

        Receta existente = buscarPorId(id);

        // No permitir cambiar ID
        if (datos.getId() != null && !datos.getId().equals(id))
            throw new IllegalArgumentException("No se permite modificar el ID.");

        // Validar nombre único si lo cambian
        if (!existente.getNombre().equals(datos.getNombre())
                && repo.existsByNombre(datos.getNombre()))
            throw new RecursoDuplicadoException("Ya existe otra receta con ese nombre.");

        // Actualizar
        existente.setNombre(datos.getNombre());
        existente.setPesoRacion(datos.getPesoRacion());
        existente.setCalorias(datos.getCalorias());

        return repo.save(existente);
    }

    @Override
    public void eliminar(Integer id) {
        Receta existente = buscarPorId(id);
        repo.delete(existente);  // eliminación real
    }
}

