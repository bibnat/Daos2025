package daos.tp.centroasistencias.controladores;

import daos.tp.centroasistencias.dto.AsistidoDTO;
import daos.tp.centroasistencias.dto.AsistidoRequestDTO;
import daos.tp.centroasistencias.servicios.AsistidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asistidos")
public class AsistidoController {

    private final AsistidoService asistidoService;

    public AsistidoController(AsistidoService asistidoService) {
        this.asistidoService = asistidoService;
    }

    // -------------------- CREAR --------------------
    @PostMapping
    public AsistidoDTO guardar(@RequestBody AsistidoRequestDTO dto) {
        return asistidoService.guardar(dto);
    }

    // -------------------- LISTAR --------------------
    @GetMapping
    public List<AsistidoDTO> listar() {
        return asistidoService.listar();
    }

    // -------------------- GET POR ID --------------------
    @GetMapping("/{id}")
    public AsistidoDTO getById(@PathVariable Long id) {
        return asistidoService.buscarPorId(id);
    }

    // -------------------- ACTUALIZAR --------------------
    @PutMapping("/{id}")
    public AsistidoDTO actualizar(@PathVariable Long id, @RequestBody AsistidoRequestDTO dto) {
        return asistidoService.actualizar(id, dto);
    }

    // -------------------- ELIMINAR --------------------
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        asistidoService.eliminar(id);
    }
}
