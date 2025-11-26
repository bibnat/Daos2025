package daos.tp.centroasistencias.dto;

import daos.tp.centroasistencias.entidades.Racion;
import org.springframework.hateoas.RepresentationModel;

public class RacionDTO extends RepresentationModel<RacionDTO> {

    public Long id;
    public Integer stockPreparado;
    public Integer stockRestante;
    public Long recetaId;

    public RacionDTO(Racion r) {
        this.id = r.getId();
        this.stockPreparado = r.getStockPreparado();
        this.stockRestante = r.getStockRestante();
        this.recetaId = r.getRecetaId();
    }
}
