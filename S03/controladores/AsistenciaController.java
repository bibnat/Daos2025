package tuti.daos.presentacion;

import org.springframework.web.bind.annotation.*;
import tuti.daos.entidades.Asistencia;
import tuti.daos.presentacion.dto.AsistenciaDTO;
import tuti.daos.servicios.AsistenciaService;

import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
public class AsistenciaController {

    private final AsistenciaService service;

    public AsistenciaController(AsistenciaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Asistencia> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Asistencia obtener(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/asistido/{idAsistido}")
    public List<Asistencia> listarPorAsistido(@PathVariable Integer idAsistido) {
        return service.listarPorAsistido(idAsistido);
    }

    @PostMapping
    public Asistencia crear(@RequestBody AsistenciaDTO dto) {
        return service.crearDesdeDTO(dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}

