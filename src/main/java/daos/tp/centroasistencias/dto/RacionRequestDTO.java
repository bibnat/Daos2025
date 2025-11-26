package daos.tp.centroasistencias.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class RacionRequestDTO {

    @Positive(message = "El stock preparado debe ser positivo")
    public Integer stockPreparado;

    @NotNull(message = "Debe indicar la receta")
    public Long recetaId;

    @NotNull(message = "La fecha de preparación es obligatoria")
    public LocalDate fechaPreparacion;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    public LocalDate fechaVencimiento;
}
