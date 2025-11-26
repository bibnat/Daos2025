package daos.tp.centroasistencias.controladores;

import daos.tp.centroasistencias.dto.AsistenciaDTO;
import daos.tp.centroasistencias.dto.AsistenciaRequestDTO;
import daos.tp.centroasistencias.servicios.AsistenciaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
public class AsistenciaController {

    private final AsistenciaService service;

    public AsistenciaController(AsistenciaService service) {
        this.service = service;
    }

    @PostMapping
    public AsistenciaDTO crear(@RequestBody AsistenciaRequestDTO dto) {
        return service.crear(dto);
    }

    @GetMapping
    public List<AsistenciaDTO> listar(
            @RequestParam(required = false) Long racionId) {

        if (racionId != null)
            return service.listarPorRacion(racionId);

        return service.listar();
    }

    @GetMapping("/{id}")
    public AsistenciaDTO buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public AsistenciaDTO actualizar(
            @PathVariable Long id,
            @RequestBody AsistenciaRequestDTO dto) {

        return service.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
