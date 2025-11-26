package daos.tp.centroasistencias.servicios;

import daos.tp.centroasistencias.dto.AsistenciaDTO;
import daos.tp.centroasistencias.dto.AsistenciaRequestDTO;
import daos.tp.centroasistencias.entidades.Asistencia;
import daos.tp.centroasistencias.entidades.Racion;
import daos.tp.centroasistencias.repositorios.AsistenciaRepository;
import daos.tp.centroasistencias.repositorios.AsistidoRepository;
import daos.tp.centroasistencias.repositorios.RacionRepository;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsistenciaService {

    private final AsistenciaRepository repo;
    private final RacionRepository racionRepo;
    private final AsistidoRepository asistidoRepo;

    public AsistenciaService(
            AsistenciaRepository repo,
            RacionRepository racionRepo,
            AsistidoRepository asistidoRepo) {

        this.repo = repo;
        this.racionRepo = racionRepo;
        this.asistidoRepo = asistidoRepo;
    }



    // --------------------------------------------------
    // ---------------      CREAR        -----------------
    // --------------------------------------------------
    public AsistenciaDTO crear(AsistenciaRequestDTO dto) {

        // validar existencia asistido
        asistidoRepo.findById(dto.asistidoId)
                .orElseThrow(() -> new RuntimeException("Asistido no existe"));

        // validar existencia racion
        Racion r = racionRepo.findById(dto.racionId)
                .orElseThrow(() -> new RuntimeException("Ración no existe"));

        // validar fecha
        if (dto.fechaEntrega.isAfter(r.getFechaVencimiento())) {
            throw new RuntimeException("La fecha de entrega no puede ser posterior al vencimiento");
        }

        // validar stock
        if (r.getStockRestante() <= 0) {
            throw new RuntimeException("No hay stock disponible de esta ración");
        }

        // actualizar stock
        r.setStockRestante(r.getStockRestante() - 1);
        racionRepo.save(r);

        // crear asistencia
        Asistencia a = new Asistencia();
        a.setAsistidoId(dto.asistidoId);
        a.setRacionId(dto.racionId);
        a.setFechaEntrega(dto.fechaEntrega);

        repo.save(a);

        return agregarLinks(new AsistenciaDTO(a),
                r.getRecetaId());
    }



    // --------------------------------------------------
    // ---------------  LISTAR GENERAL   ----------------
    // --------------------------------------------------
    public List<AsistenciaDTO> listar() {
        return repo.findAll()
                .stream()
                .map(a -> {

                    Racion r = racionRepo.findById(a.getRacionId())
                            .orElseThrow(() -> new RuntimeException("Ración no encontrada"));

                    return agregarLinks(new AsistenciaDTO(a),
                            r.getRecetaId());
                })
                .toList();
    }



    // --------------------------------------------------
    // -----------   LISTAR POR RACION   ----------------
    // --------------------------------------------------
    public List<AsistenciaDTO> listarPorRacion(Long racionId) {

        // validar que la ración exista
        racionRepo.findById(racionId)
                .orElseThrow(() -> new RuntimeException("Ración no encontrada"));

        return repo.findAll().stream()
                .filter(a -> a.getRacionId().equals(racionId))
                .map(a -> {
                    Racion r = racionRepo.findById(a.getRacionId())
                            .orElseThrow(() -> new RuntimeException("Ración no encontrada"));

                    return agregarLinks(new AsistenciaDTO(a),
                            r.getRecetaId());
                })
                .toList();
    }



    // --------------------------------------------------
    // ------------------  BUSCAR       -----------------
    // --------------------------------------------------
    public AsistenciaDTO buscar(Long id) {

        Asistencia a = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada"));

        Racion r = racionRepo.findById(a.getRacionId())
                .orElseThrow(() -> new RuntimeException("Ración no encontrada"));

        return agregarLinks(new AsistenciaDTO(a),
                r.getRecetaId());
    }



    // --------------------------------------------------
    // ------------------ ACTUALIZAR    -----------------
    // --------------------------------------------------
    public AsistenciaDTO actualizar(Long id, AsistenciaRequestDTO dto) {

        Asistencia a = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada"));

        // validar existencia asistido
        asistidoRepo.findById(dto.asistidoId)
                .orElseThrow(() -> new RuntimeException("Asistido no existe"));

        // validar existencia ración
        Racion nuevaRacion = racionRepo.findById(dto.racionId)
                .orElseThrow(() -> new RuntimeException("Ración no existe"));

        // validar fecha
        if (dto.fechaEntrega.isAfter(nuevaRacion.getFechaVencimiento())) {
            throw new RuntimeException("La fecha de entrega no puede ser posterior al vencimiento");
        }

        // si cambia la ración, hay que mover stock
        if (!dto.racionId.equals(a.getRacionId())) {

            // restaurar stock de la ración anterior
            Racion viejaRacion = racionRepo.findById(a.getRacionId())
                    .orElseThrow(() -> new RuntimeException("Ración anterior no encontrada"));

            viejaRacion.setStockRestante(viejaRacion.getStockRestante() + 1);
            racionRepo.save(viejaRacion);

            // descontar stock de la nueva
            if (nuevaRacion.getStockRestante() <= 0) {
                throw new RuntimeException("La nueva ración no tiene stock");
            }
            nuevaRacion.setStockRestante(nuevaRacion.getStockRestante() - 1);
            racionRepo.save(nuevaRacion);
        }

        // actualizar datos
        a.setAsistidoId(dto.asistidoId);
        a.setRacionId(dto.racionId);
        a.setFechaEntrega(dto.fechaEntrega);

        repo.save(a);

        return agregarLinks(new AsistenciaDTO(a),
                nuevaRacion.getRecetaId());
    }



    // --------------------------------------------------
    // ------------------  ELIMINAR     -----------------
    // --------------------------------------------------
    public void eliminar(Long id) {
        repo.deleteById(id);
    }



    // --------------------------------------------------
    // ------------------   HATEOAS     -----------------
    // --------------------------------------------------
    private AsistenciaDTO agregarLinks(AsistenciaDTO dto, Long recetaId) {

        dto.add(Link.of("/api/asistencias/" + dto.id).withSelfRel());
        dto.add(Link.of("/api/asistidos/" + dto.asistidoId).withRel("asistido"));
        dto.add(Link.of("/api/raciones/" + dto.racionId).withRel("racion"));
        dto.add(Link.of("/api/recetas/" + recetaId).withRel("receta"));

        return dto;
    }
}
