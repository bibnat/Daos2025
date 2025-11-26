package daos.tp.centroasistencias.servicios;

import daos.tp.centroasistencias.dto.RacionDTO;
import daos.tp.centroasistencias.dto.RacionRequestDTO;
import daos.tp.centroasistencias.entidades.Racion;
import daos.tp.centroasistencias.repositorios.RacionRepository;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RacionService {

    private final RacionRepository repo;

    public RacionService(RacionRepository repo) {
        this.repo = repo;
    }

    // ------------------ CREAR ------------------
    public RacionDTO crear(RacionRequestDTO dto) {

        validarFechas(dto);

        Racion r = new Racion();
        r.setStockPreparado(dto.stockPreparado);

        // REGLA IMPORTANTE DEL TP: stockRestante = stockPreparado
        r.setStockRestante(dto.stockPreparado);

        r.setRecetaId(dto.recetaId);
        r.setFechaPreparacion(dto.fechaPreparacion);
        r.setFechaVencimiento(dto.fechaVencimiento);

        repo.save(r);

        return agregarLinks(new RacionDTO(r));
    }

    // ------------------ ACTUALIZAR ------------------
    public RacionDTO actualizar(Long id, RacionRequestDTO dto) {

        Racion r = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ración no encontrada"));

        validarFechas(dto);

        r.setStockPreparado(dto.stockPreparado);

        // REGLA: stockRestante NO cambia en S02
        // r.setStockRestante(...) → PROHIBIDO

        r.setRecetaId(dto.recetaId);
        r.setFechaPreparacion(dto.fechaPreparacion);
        r.setFechaVencimiento(dto.fechaVencimiento);

        repo.save(r);

        return agregarLinks(new RacionDTO(r));
    }

    // ------------------ LISTAR ------------------
    public List<RacionDTO> listar() {
        return repo.findAll()
                .stream()
                .map(r -> agregarLinks(new RacionDTO(r)))
                .toList();
    }

    // ------------------ BUSCAR ------------------
    public RacionDTO buscar(Long id) {
        Racion r = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ración no encontrada"));
        return agregarLinks(new RacionDTO(r));
    }

    // ------------------ ELIMINAR ------------------
    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    // ------------------ VALIDACIONES ------------------
    private void validarFechas(RacionRequestDTO dto) {
        if (dto.fechaVencimiento.isBefore(dto.fechaPreparacion)) {
            throw new RuntimeException("La fecha de vencimiento debe ser posterior a la de preparación");
        }
    }

    // ------------------ HATEOAS ------------------
 // ------------------ HATEOAS ------------------
    private RacionDTO agregarLinks(RacionDTO dto) {

        // Link a la receta (S04)
        dto.add(Link.of("/api/recetas/" + dto.recetaId).withRel("receta"));

        // Link a las asistencias que usaron esta ración (S03)
        dto.add(Link.of("/api/asistencias?racionId=" + dto.id).withRel("asistencias"));

        // self link
        dto.add(Link.of("/api/raciones/" + dto.id).withSelfRel());

        return dto;
    }

}
