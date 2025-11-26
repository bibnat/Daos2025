package daos.tp.centroasistencias.controladores;

import daos.tp.centroasistencias.dto.RacionDTO;
import daos.tp.centroasistencias.dto.RacionRequestDTO;
import daos.tp.centroasistencias.servicios.RacionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/raciones")
public class RacionController {

    private final RacionService service;

    public RacionController(RacionService service) {
        this.service = service;
    }

    @PostMapping
    public RacionDTO crear(@RequestBody RacionRequestDTO dto) {
        return service.crear(dto);
    }

    @PutMapping("/{id}")
    public RacionDTO actualizar(
            @PathVariable Long id,
            @RequestBody RacionRequestDTO dto) {
        return service.actualizar(id, dto);
    }

    @GetMapping
    public List<RacionDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public RacionDTO buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
