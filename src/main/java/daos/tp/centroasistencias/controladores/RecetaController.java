package daos.tp.centroasistencias.controladores;

import daos.tp.centroasistencias.dto.RacionDTO;
import daos.tp.centroasistencias.dto.RecetaDTO;
import daos.tp.centroasistencias.dto.RecetaRequestDTO;
import daos.tp.centroasistencias.servicios.RecetaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController {

    private final RecetaService service;

    public RecetaController(RecetaService service) {
        this.service = service;
    }

    @PostMapping
    public RecetaDTO crear(@RequestBody RecetaRequestDTO dto) {
        return service.crear(dto);
    }

    @PutMapping("/{id}")
    public RecetaDTO actualizar(@PathVariable Long id,
                                @RequestBody RecetaRequestDTO dto) {
        return service.actualizar(id, dto);
    }

    @GetMapping
    public List<RecetaDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public RecetaDTO buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
    
    @GetMapping("/{id}/raciones")
    public List<RacionDTO> listarRacionesPorReceta(@PathVariable Long id) {
        return service.listarRacionesPorReceta(id);
    }

    
}
