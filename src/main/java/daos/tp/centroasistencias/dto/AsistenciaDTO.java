package daos.tp.centroasistencias.dto;

import daos.tp.centroasistencias.entidades.Asistencia;
import org.springframework.hateoas.RepresentationModel;

public class AsistenciaDTO extends RepresentationModel<AsistenciaDTO> {

    public Long id;
    public Long asistidoId;
    public Long racionId;
    public String fechaEntrega;

    public AsistenciaDTO(Asistencia a) {
        this.id = a.getId();
        this.asistidoId = a.getAsistidoId();
        this.racionId = a.getRacionId();
        this.fechaEntrega = a.getFechaEntrega().toString();
    }
}
