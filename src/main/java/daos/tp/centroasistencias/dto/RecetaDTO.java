package daos.tp.centroasistencias.dto;

import daos.tp.centroasistencias.entidades.Receta;
import org.springframework.hateoas.RepresentationModel;

public class RecetaDTO extends RepresentationModel<RecetaDTO> {

    public Long id;
    public String nombre;
    public Double pesoRacion;
    public Integer caloriasRacion;

    public RecetaDTO(Receta r) {
        this.id = r.getId();
        this.nombre = r.getNombre();
        this.pesoRacion = r.getPesoRacion();
        this.caloriasRacion = r.getCaloriasRacion();
    }
}
