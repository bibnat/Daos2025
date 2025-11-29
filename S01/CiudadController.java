package tuti.daos.presentacion;

import org.springframework.web.bind.annotation.*;
import tuti.daos.entidades.Ciudad;
import tuti.daos.accesoDatos.CiudadRepository;
import tuti.daos.excepciones.RecursoNoEncontradoException;

import java.util.List;

@RestController
@RequestMapping("/api/ciudades")
public class CiudadController {

    private final CiudadRepository repo;

    public CiudadController(CiudadRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Ciudad> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Ciudad obtener(@PathVariable Integer id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Ciudad no encontrada: " + id)
                );
    }
}
